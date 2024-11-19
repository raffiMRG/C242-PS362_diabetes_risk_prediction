const admin = require("firebase-admin");
const bcrypt = require("bcryptjs");

const loginHandler = async (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).send("Lengkapi semua data.");
  }

  try {
    // Cari pengguna berdasarkan username
    const userRef = await admin.firestore().collection("users").doc(username).get();

    if (!userRef.exists) {
      return res.status(400).send("Username tidak ditemukan.");
    }

    const user = userRef.data();
    
    // Verifikasi password
    const isPasswordValid = await bcrypt.compare(password, user.password);
    
    if (!isPasswordValid) {
      return res.status(400).send("Password salah.");
    }

    // Respons sukses
    return res.status(200).send("Selamat datang.");
  } catch (error) {
    console.error("Error login: ", error);
    return res.status(500).send("Gagal login.");
  }
};

module.exports = loginHandler;
