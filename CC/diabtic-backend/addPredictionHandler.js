const axios = require('axios');
const { Firestore } = require('@google-cloud/firestore');
const jwt = require('jsonwebtoken');

// Inisialisasi Firestore client
const firestore = new Firestore();

const addPredictionHandler = async (req, res) => {
  const authHeader = req.headers.authorization;

  if (!authHeader || !authHeader.startsWith("Bearer ")) {
    return res.status(401).json({
      success: false,
      message: "Unauthorized.",
    });
  }

  const token = authHeader.split(" ")[1];

  try {
    // Verifikasi token
    const decoded = jwt.verify(token, process.env.JWT_SECRET_KEY);
    const username = decoded.username;

    // Validasi input dari body
    const { age, gender, bmi, smoking, alcohol, activity } = req.body;

    if (
      age == null ||
      gender == null ||
      bmi == null ||
      smoking == null ||
      alcohol == null ||
      activity == null
    ) {
      return res.status(400).json({
        success: false,
        message: "Lengkapi semua data prediksi.",
      });
    }
    

    // Call Cloud Run API for prediction
    const predictionResponse = await axios.post('https://diabetes-893955223741.asia-southeast2.run.app/predict', {
      age, gender, bmi, smoking, alcohol, activity
    });

    const predictionResult = predictionResponse.data.prediction;

    // Ambil referensi ke koleksi prediksi pengguna
    const userRef = firestore.collection("users").doc(username);
    const predictionsRef = userRef.collection("predictions");

    // Tambahkan data prediksi
    const newPrediction = {
      predictionResult,
      predictionDetails: { age, gender, bmi, smoking, alcohol, activity },
      predictionSuggestion: predictionResult === 'Yes' ? 'Risky' : 'Not risky',
      createdAt: Firestore.FieldValue.serverTimestamp(),
    };

    const addedPrediction = await predictionsRef.add(newPrediction);

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Prediksi berhasil disimpan.",
      data: {
        username,
        predictionId: addedPrediction.id,
      },
    });
  } catch (error) {
    console.error("Error menambahkan prediksi: ", error);
    return res.status(500).json({
      success: false,
      message: "Kesalahan saat menyimpan prediksi.",
    });
  }
};

module.exports = addPredictionHandler;
