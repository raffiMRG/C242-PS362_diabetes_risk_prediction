const { Firestore } = require("@google-cloud/firestore");
const jwt = require("jsonwebtoken");

// Inisialisasi Firestore client
const firestore = new Firestore();

const getPredictionHandler = async (req, res) => {
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

    // Referensi dokumen pengguna
    const userRef = firestore.collection("users").doc(username);
    const userDoc = await userRef.get();

    if (!userDoc.exists) {
      return res.status(404).json({
        success: false,
        message: "Pengguna tidak ditemukan.",
      });
    }

    // Ambil data prediksi dari dokumen pengguna
    const userData = userDoc.data();
    const predictions = userData.predictions || [];

    if (predictions.length === 0) {
      return res.status(404).json({
        success: false,
        message: "Tidak ada riwayat prediksi ditemukan.",
      });
    }

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Riwayat prediksi berhasil diambil.",
      data: predictions,
    });
  } catch (error) {
    console.error("Error mendapatkan prediksi: ", error);
    return res.status(500).json({
      success: false,
      message: "Kesalahan saat mengambil data prediksi.",
    });
  }
};

module.exports = getPredictionHandler;
