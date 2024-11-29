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

    // Panggil Cloud Run API untuk prediksi
    const predictionResponse = await axios.post(
      'https://diabetes-893955223741.asia-southeast2.run.app/predict',
      { age, gender, bmi, smoking, alcohol, activity }
    );

    const predictionResult = predictionResponse.data.prediction;

    // Ambil referensi pengguna di Firestore
    const userRef = firestore.collection("users").doc(username);
    const userDoc = await userRef.get();

    if (!userDoc.exists) {
      return res.status(404).json({
        success: false,
        message: "Pengguna tidak ditemukan.",
      });
    }

    // Ambil data pengguna dan prediksi yang sudah ada
    const userData = userDoc.data();
    const existingPredictions = userData.predictions || [];

    // Tambahkan prediksi baru ke array
    const newPrediction = {
      id: `prediction-${Date.now()}`, // ID unik untuk setiap prediksi
      predictionResult,
      predictionDetails: { age, gender, bmi, smoking, alcohol, activity },
      predictionSuggestion: predictionResult === 'Yes' ? 'Risky' : 'Not risky',
      createdAt: new Date().toISOString(),
    };

    const updatedPredictions = [...existingPredictions, newPrediction];

    // Perbarui dokumen pengguna dengan prediksi baru
    await userRef.update({
      predictions: updatedPredictions,
    });

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Prediksi berhasil disimpan.",
      data: {
        username,
        prediction: newPrediction,
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
