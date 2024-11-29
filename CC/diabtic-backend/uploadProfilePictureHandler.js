const { Storage } = require('@google-cloud/storage');
const { Firestore } = require('@google-cloud/firestore');

// Inisialisasi Cloud Storage dan Firestore
const storage = new Storage();
const firestore = new Firestore();

// Nama bucket Google Cloud Storage untuk foto profil
const bucketName = process.env.GCS_BUCKET_NAME; 

const uploadProfilePictureHandler = async (req, res) => {
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
    
    // Upload file ke Cloud Storage
    const bucket = storage.bucket(bucketName);
    const blob = bucket.file(fileName);
    const blobStream = blob.createWriteStream({
      resumable: false,
      contentType: file.mimetype,
    });

    // Menulis file ke Cloud Storage
    blobStream.on('finish', async () => {
      // Mendapatkan URL file yang baru di-upload
      const profilePictureUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;

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
    });

    // Menulis file ke Cloud Storage
    blobStream.end(file.buffer);
  } catch (error) {
    console.error("Error upload gambar profil: ", error);
    return res.status(500).json({
      success: false,
      message: "Gagal meng-upload foto profil.",
    });
  }
};

module.exports = uploadProfilePictureHandler;
