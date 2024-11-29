const express = require("express");
const loginHandler = require("./loginHandler");
const registerHandler = require("./registerHandler");
const refreshTokenHandler = require("./refreshTokenHandler");
const logoutHandler = require("./logoutHandler");
const getAccountHandler = require("./getAccountHandler");
const addPredictionHandler = require("./addPredictionHandler");
const getPredictionHandler = require("./getPredictionHandler");

const app = express();
app.use(express.json());

// Route Handlers
app.post("/login", loginHandler);
app.post("/register", registerHandler);
app.post("/logout", logoutHandler);
app.post("/refresh-token", refreshTokenHandler);
app.get("/account", getAccountHandler);
app.post("/account/predictions", addPredictionHandler);
app.get("/account/predictions", getPredictionHandler);

// Default route
app.get("/", (req, res) => {
  res.send("Welcome to the authentication API!");
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
