const jwt = require("jsonwebtoken");
const { Firestore } = require('@google-cloud/firestore');

// Inisialisasi Firestore client
const firestore = new Firestore();

const refreshTokenHandler = async (req, res) => {
  const { refreshToken } = req.body;

  if (!refreshToken) {
    return res.status(400).json({
      success: false,
      message: "Refresh token diperlukan.",
    });
  }

  try {
    // Verifikasi Refresh Token
    const decoded = jwt.verify(refreshToken, process.env.JWT_REFRESH_SECRET_KEY);
    const username = decoded.username;

    // Cari pengguna berdasarkan username dan refresh token
    const userRef = firestore.collection("users").doc(username);
    const userDoc = await userRef.get();

    if (!userDoc.exists || userDoc.data().refreshToken !== refreshToken) {
      return res.status(400).json({
        success: false,
        message: "Invalid refresh token.",
      });
    }

    // Generate JWT Access Token baru
    const accessToken = jwt.sign(
      { username: username },
      process.env.JWT_SECRET_KEY,
      { expiresIn: "1h" }
    );

    // Respons sukses dengan access token baru
    return res.status(200).json({
      success: true,
      message: "Token refresh berhasil.",
      data: {
        accessToken,
      },
    });
  } catch (error) {
    console.error("Error refresh token: ", error);
    return res.status(500).json({
      success: false,
      message: "Gagal melakukan refresh token.",
    });
  }
};

module.exports = refreshTokenHandler;