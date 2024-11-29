const express = require("express");
const multer = require("multer");
const loginHandler = require("./loginHandler");
const registerHandler = require("./registerHandler");
const refreshTokenHandler = require("./refreshTokenHandler");
const logoutHandler = require("./logoutHandler");
const getAccountHandler = require("./getAccountHandler");
const addPredictionHandler = require("./addPredictionHandler");
const getPredictionHandler = require("./getPredictionHandler");
const editProfilePictureHandler = require("./editProfilePictureHandler");
const deleteProfilePictureHandler = require("./deleteProfilePictureHandler");
const uploadProfilePictureHandler = require("./uploadProfilePictureHandler"); // Menambahkan handler untuk upload foto profil

const app = express();
app.use(express.json());

// Setup multer untuk menyimpan file dalam memory
const storage = multer.memoryStorage(); 
const upload = multer({ storage: storage });

// Route Handlers
app.post("/login", loginHandler);
app.post("/register", registerHandler);
app.post("/logout", logoutHandler);
app.post("/refresh-token", refreshTokenHandler);
app.get("/account", getAccountHandler);
app.post("/account/predictions", addPredictionHandler);
app.get("/account/predictions", getPredictionHandler);

// Route untuk upload gambar profil
app.post("/account/upload-profile-picture", upload.single('profilePicture'), uploadProfilePictureHandler);

// Route untuk edit gambar profil
app.post("/account/edit-profile-picture", upload.single('profilePicture'), editProfilePictureHandler);

// Route untuk hapus gambar profil
app.delete("/account/delete-profile-picture", deleteProfilePictureHandler);

// Default route
app.get("/", (req, res) => {
  res.send("Welcome to the authentication API!");
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
