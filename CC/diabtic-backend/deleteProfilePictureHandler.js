const { Storage } = require('@google-cloud/storage');
const { Firestore } = require('@google-cloud/firestore');
const jwt = require('jsonwebtoken');

// Inisialisasi Cloud Storage dan Firestore
const storage = new Storage();
const firestore = new Firestore();

// Nama bucket Google Cloud Storage untuk foto profil
const bucketName = process.env.GCS_BUCKET_NAME;

const deleteProfilePictureHandler = async (req, res) => {
  const authHeader = req.headers.authorization;

  if (!authHeader || !authHeader.startsWith("Bearer ")) {
    return res.status(401).json({
      success: false,
      message: "Token tidak ditemukan.",
    });
  }

  const token = authHeader.split(" ")[1];

  try {
    // Verifikasi token
    const decoded = jwt.verify(token, process.env.JWT_SECRET_KEY);
    const username = decoded.username;

    // Ambil data pengguna dan URL foto profil dari Firestore
    const userRef = firestore.collection("users").doc(username);
    const userDoc = await userRef.get();
    if (!userDoc.exists) {
      return res.status(404).json({
        success: false,
        message: "Pengguna tidak ditemukan.",
      });
    }

    const userData = userDoc.data();
    const profilePicture = userData.profilePicture;

    // Cek apakah pengguna sudah memiliki foto profil
    if (!profilePicture) {
      return res.status(400).json({
        success: false,
        message: "Tidak ada foto profil untuk dihapus.",
      });
    }

    // Hapus gambar profil dari Google Cloud Storage
    const fileName = profilePicture.split("/").pop();
    const file = storage.bucket(bucketName).file(fileName);
    await file.delete();

    // Set field profilePicture menjadi null di Firestore
    await userRef.update({
      profilePicture: null,
    });

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Foto profil berhasil dihapus.",
    });
  } catch (error) {
    console.error("Error hapus gambar profil: ", error);
    return res.status(500).json({
      success: false,
      message: "Gagal menghapus foto profil.",
    });
  }
};

module.exports = deleteProfilePictureHandler;
