const express = require("express");
const multer = require("multer");
const loginHandler = require("./loginHandler");
const registerHandler = require("./registerHandler");
const refreshTokenHandler = require("./refreshTokenHandler");
const logoutHandler = require("./logoutHandler");
const getAccountHandler = require("./getAccountHandler");
const addPredictionHandler = require("./addPredictionHandler");
const getPredictionHandler = require("./getPredictionHandler");
const uploadProfilePictureHandler = require("./uploadProfilePictureHandler");
const editProfilePictureHandler = require("./editProfilePictureHandler");
const deleteProfilePictureHandler = require("./deleteProfilePictureHandler");
const editAccountHandler = require("./editAccountHandler");

const app = express();

// Middleware untuk parsing JSON body
app.use(express.json());

// Setup multer untuk menyimpan file dalam memory (jika Anda ingin menangani upload file)
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

// Upload dan manipulasi foto profil
app.post("/account/upload-profile-picture", upload.single('profilePicture'), uploadProfilePictureHandler);
app.post("/account/edit-profile-picture", upload.single('profilePicture'), editProfilePictureHandler);
app.delete("/account/delete-profile-picture", deleteProfilePictureHandler);

app.patch("/account/edit", editAccountHandler);

// Default route
app.get("/", (req, res) => {
  res.send("Welcome to the authentication API!");
});

// Global error handling middleware
app.use((err, req, res, next) => {
  console.error(err);
  res.status(500).json({
    success: false,
    message: "Terjadi kesalahan internal.",
  });
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
