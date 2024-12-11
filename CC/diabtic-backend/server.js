const express = require("express");
const multer = require("multer");
const cors = require("cors");

const loginHandler = require("./loginHandler");
const registerHandler = require("./registerHandler");
const refreshTokenHandler = require("./refreshTokenHandler");
const logoutHandler = require("./logoutHandler");
const getAccountHandler = require("./getAccountHandler");
const addPredictionHandler = require("./addPredictionHandler");
const getPredictionHandler = require("./getPredictionHandler");
const editAccountHandler = require("./editAccountHandler");

// Artikel
const getArticleHandler = require('./getArticleHandler');
const addArticleHandler = require('./addArticleHandler');
const editArticleHandler = require('./editArticleHandler');

// Profile Picture
const { uploadProfilePictureHandler, editProfilePictureHandler, deleteProfilePictureHandler } = require('./profilePictureHandler');

const app = express();

// Middleware untuk parsing JSON body
app.use(express.json());

// Setup multer untuk menyimpan file dalam memory
const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

// Middleware untuk parsing request body
app.use(cors());

// Route Handlers
app.post("/auth/login", loginHandler);
app.post("/auth/register", registerHandler);
app.post("/auth/logout", logoutHandler);
app.post("/auth/refresh-token", refreshTokenHandler);

app.get("/users/profile", getAccountHandler);
app.patch("/users/profile", editAccountHandler);

app.post("/users/predictions", addPredictionHandler);
app.get("/users/predictions", getPredictionHandler);

// Upload dan manipulasi foto profil
app.post("/users/profile/profile-picture", upload.single('profilePicture'), uploadProfilePictureHandler);
app.put("/users/profile/profile-picture", upload.single('profilePicture'), editProfilePictureHandler);
app.delete("/users/profile/profile-picture", deleteProfilePictureHandler);

// Route artikel
app.get("/articles/:id?", getArticleHandler);
app.post("/add-article", addArticleHandler);
app.patch("/articles/:id", editArticleHandler);

// Default route
app.get("/", (req, res) => {
  res.send("Welcome to the diabetic authentication API!");
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
