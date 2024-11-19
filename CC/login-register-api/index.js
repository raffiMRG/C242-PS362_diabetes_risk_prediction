const express = require("express");
const admin = require("firebase-admin");
const functions = require("firebase-functions");

// Mengimpor handler
const registerHandler = require("./registerHandler");
const loginHandler = require("./loginHandler");

const app = express();

// Inisialisasi Firebase Admin SDK
admin.initializeApp();

// Middleware untuk parse JSON
app.use(express.json());

// Route untuk register pengguna
app.post("/register", registerHandler);

// Route untuk login pengguna
app.post("/login", loginHandler);

// Cloud Function untuk menjalankan Express app
exports.diabticUserAuth = functions.https.onRequest(app);
