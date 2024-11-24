const { Firestore } = require('@google-cloud/firestore');

// Inisialisasi Firestore client
const firestore = new Firestore();

const logoutHandler = async (req, res) => {
  const { username } = req.body;

  if (!username) {
    return res.status(400).json({
      success: false,
      message: "Username diperlukan.",
    });
  }

  try {
    // Hapus refresh token pengguna dari Firestore
    const userRef = firestore.collection("users").doc(username);
    await userRef.update({
      refreshToken: Firestore.FieldValue.delete(),
    });

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Anda telah logout.",
    });
  } catch (error) {
    console.error("Error logout: ", error);
    return res.status(500).json({
      success: false,
      message: "Gagal logout.",
    });
  }
};

module.exports = logoutHandler;
