const { Firestore } = require('@google-cloud/firestore');
const jwt = require('jsonwebtoken');

// Inisialisasi Firestore client
const firestore = new Firestore();

const addPredictionHandler = async (req, res) => {
  const { prediction, riskLevel } = req.body;
  const authHeader = req.headers.authorization;

  // Periksa apakah ada token
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({
      success: false,
      message: 'Unauthorized. Token tidak ditemukan.',
    });
  }

  const token = authHeader.split(' ')[1];

  try {
    // Verifikasi token JWT
    const decoded = jwt.verify(token, process.env.JWT_SECRET_KEY);
    const username = decoded.username;

    // Periksa apakah username valid
    const userRef = firestore.collection('users').doc(username);
    const userDoc = await userRef.get();
    if (!userDoc.exists) {
      return res.status(404).json({
        success: false,
        message: 'Pengguna tidak ditemukan.',
      });
    }

    // Menambahkan data prediksi ke subkoleksi predictions
    const predictionsRef = userRef.collection('predictions');
    const newPredictionRef = predictionsRef.doc(); // Auto-generate ID
    await newPredictionRef.set({
      prediction,
      riskLevel,
      createdAt: Firestore.FieldValue.serverTimestamp(),
    });

    return res.status(200).json({
      success: true,
      message: 'Prediksi berhasil ditambahkan ke riwayat.',
    });
  } catch (error) {
    console.error('Error menambahkan prediksi:', error);
    return res.status(500).json({
      success: false,
      message: 'Gagal menambahkan prediksi.',
    });
  }
};

module.exports = addPredictionHandler;
