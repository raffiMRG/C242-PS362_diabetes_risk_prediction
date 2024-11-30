const { Storage } = require('@google-cloud/storage');
const { Firestore } = require('@google-cloud/firestore');
const jwt = require('jsonwebtoken');

// Inisialisasi Cloud Storage dan Firestore
const storage = new Storage();
const firestore = new Firestore();

// Nama bucket Google Cloud Storage untuk foto profil
const bucketName = process.env.GCS_BUCKET_NAME;

// Fungsi untuk verifikasi token JWT
const verifyToken = (token) => {
  try {
    return jwt.verify(token, process.env.JWT_SECRET_KEY);
  } catch (error) {
    throw new Error('Token tidak valid atau telah kedaluwarsa.');
  }
};

const deleteProfilePictureHandler = async (req, res) => {
  const authHeader = req.headers.authorization;

  // Validasi token
  if (!authHeader || !authHeader.startsWith("Bearer ")) {
    return res.status(401).json({
      success: false,
      message: "Token tidak ditemukan.",
    });
  }

  const token = authHeader.split(" ")[1];

  try {
    // Verifikasi token
    const decoded = verifyToken(token);
    const { username } = decoded;

    // Ambil data pengguna dari Firestore
    const userRef = firestore.collection("users").doc(username);
    const userDoc = await userRef.get();

    if (!userDoc.exists) {
      return res.status(404).json({
        success: false,
        message: "Pengguna tidak ditemukan.",
      });
    }

    const { profilePicture } = userDoc.data();

    // Cek apakah pengguna memiliki foto profil
    if (!profilePicture) {
      return res.status(400).json({
        success: false,
        message: "Tidak ada foto profil untuk dihapus.",
      });
    }

    // Hapus gambar profil dari Google Cloud Storage
    const fileName = profilePicture.split("/").pop();
    const file = storage.bucket(bucketName).file(fileName);

    // Pastikan file ada sebelum dihapus
    const [exists] = await file.exists();
    if (!exists) {
      return res.status(404).json({
        success: false,
        message: "File foto profil tidak ditemukan di storage.",
      });
    }

    await file.delete();

    // Perbarui Firestore untuk menghapus foto profil
    await userRef.update({
      profilePicture: null,
    });

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Foto profil berhasil dihapus.",
    });
  } catch (error) {
    console.error("Error hapus gambar profil: ", error.message);
    return res.status(500).json({
      success: false,
      message: error.message || "Gagal menghapus foto profil.",
    });
  }
};

module.exports = deleteProfilePictureHandler;
