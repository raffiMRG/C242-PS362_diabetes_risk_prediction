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

// Fungsi untuk meng-upload foto profil
const uploadProfilePictureHandler = async (req, res) => {
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

    // Ambil file gambar dari request
    const file = req.file;
    if (!file) {
      return res.status(400).json({
        success: false,
        message: "Tidak ada file yang di-upload.",
      });
    }

    // Nama file untuk disimpan di Google Cloud Storage
    const fileName = `${username}/profile-pictures/${Date.now()}-${file.originalname}`;

    // Fungsi untuk meng-upload file ke Cloud Storage dan mendapatkan URL
    const profilePictureUrl = await uploadToStorage(file, fileName);

    // Update Firestore dengan URL foto profil baru
    const userRef = firestore.collection("users").doc(username);
    await userRef.update({
      profilePicture: profilePictureUrl,
    });

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Foto profil berhasil di-upload.",
      data: {
        profilePictureUrl,
      },
    });
  } catch (error) {
    console.error("Error upload gambar profil: ", error.message);
    return res.status(500).json({
      success: false,
      message: error.message || "Gagal meng-upload foto profil.",
    });
  }
};

// Fungsi untuk meng-upload file ke Cloud Storage dan mendapatkan URL
const uploadToStorage = (file, fileName) => {
  return new Promise((resolve, reject) => {
    const bucket = storage.bucket(bucketName);
    const blob = bucket.file(fileName);
    const blobStream = blob.createWriteStream({
      resumable: false,
      contentType: file.mimetype,
    });

    blobStream.on('finish', () => {
      const profilePictureUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;
      resolve(profilePictureUrl);
    });

    blobStream.on('error', (err) => reject(new Error('Gagal meng-upload gambar ke Cloud Storage: ' + err.message)));

    blobStream.end(file.buffer);
  });
};

// Fungsi untuk mengubah foto profil
const editProfilePictureHandler = async (req, res) => {
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

    // Ambil file gambar dari request
    const file = req.file;
    if (!file) {
      return res.status(400).json({
        success: false,
        message: "Tidak ada file yang di-upload.",
      });
    }

    // Ambil data pengguna dari Firestore
    const userRef = firestore.collection("users").doc(username);
    const userDoc = await userRef.get();
    if (!userDoc.exists) {
      return res.status(404).json({
        success: false,
        message: "Pengguna tidak ditemukan.",
      });
    }

    const { profilePicture: oldProfilePicture } = userDoc.data();

    // Hapus gambar profil lama dari Google Cloud Storage (jika ada)
    if (oldProfilePicture) {
      const oldFileName = oldProfilePicture.split("/").pop();
      const oldFile = storage.bucket(bucketName).file(`${username}/profile-pictures/${oldFileName}`);
      try {
        await oldFile.delete();
      } catch (err) {
        console.warn('Gagal menghapus gambar lama dari Storage:', err.message);
      }
    }

    // Nama file untuk disimpan di Google Cloud Storage
    const fileName = `${username}/profile-pictures/${Date.now()}-${file.originalname}`;

    // Upload file ke Cloud Storage
    const fileUploadPromise = new Promise((resolve, reject) => {
      const bucket = storage.bucket(bucketName);
      const blob = bucket.file(fileName);
      const blobStream = blob.createWriteStream({
        resumable: false,
        contentType: file.mimetype,
      });

      blobStream.on('finish', () => {
        const profilePictureUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;
        resolve(profilePictureUrl);
      });

      blobStream.on('error', (err) => reject(err));

      blobStream.end(file.buffer);
    });

    // Tunggu sampai upload selesai dan update Firestore
    const profilePictureUrl = await fileUploadPromise;

    // Update Firestore dengan URL foto profil baru
    await userRef.update({
      profilePicture: profilePictureUrl,
    });

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Foto profil berhasil diubah.",
      data: { profilePictureUrl },
    });
  } catch (error) {
    console.error("Error edit gambar profil: ", error.message);
    return res.status(500).json({
      success: false,
      message: error.message || "Gagal mengedit foto profil.",
    });
  }
};

// Fungsi untuk menghapus foto profil
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

    // Hapus semua file dalam folder username di bucket
    const folderPath = `${username}/`; // Folder berdasarkan username
    const [files] = await storage.bucket(bucketName).getFiles({ prefix: folderPath });

    if (files.length === 0) {
      return res.status(404).json({
        success: false,
        message: "Tidak ada file untuk dihapus di folder pengguna.",
      });
    }

    await storage.bucket(bucketName).deleteFiles({ prefix: folderPath });

    // Perbarui Firestore untuk menghapus informasi profilePicture
    await userRef.update({
      profilePicture: null,
    });

    // Respons sukses
    return res.status(200).json({
      success: true,
      message: "Folder dan foto profil berhasil dihapus.",
    });
  } catch (error) {
    console.error("Error menghapus folder profil pengguna:", error.message);
    return res.status(500).json({
      success: false,
      message: error.message || "Gagal menghapus folder profil pengguna.",
    });
  }
};

module.exports = {
  uploadProfilePictureHandler,
  editProfilePictureHandler,
  deleteProfilePictureHandler
};
