const admin = require("firebase-admin");
const bcrypt = require("bcryptjs");

const registerHandler = async (req, res) => {
  const { username, email, phone, password } = req.body;

  if (!username || !email || !phone || !password) {
    return res.status(400).send("Lengkapi semua data.");
  }

  try {
    // Cek apakah username sudah terdaftar
    const userRefByUsername = await admin.firestore().collection("users").doc(username).get();
    if (userRefByUsername.exists) {
      return res.status(400).send("Username sudah terdaftar.");
    }

    // Cek apakah email sudah terdaftar
    const userRefByEmail = await admin.firestore().collection("users").where("email", "==", email).get();
    if (!userRefByEmail.empty) {
      return res.status(400).send("Email sudah terdaftar.");
    }

    // Cek apakah nomor HP sudah terdaftar
    const userRefByPhone = await admin.firestore().collection("users").where("phone", "==", phone).get();
    if (!userRefByPhone.empty) {
      return res.status(400).send("Nomor HP sudah terdaftar.");
    }

    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Simpan data pengguna di Firestore dengan ID berdasarkan username
    await admin.firestore().collection("users").doc(username).set({
      username,
      email,
      phone,
      password: hashedPassword
    });

    // Respons sukses
    return res.status(200).send("Pendaftaran berhasil, silahkan login.");
  } catch (error) {
    console.error("Error registrasi: ", error);
    return res.status(500).send("Gagal melakukan pendaftaran");
  }
};

module.exports = registerHandler;
