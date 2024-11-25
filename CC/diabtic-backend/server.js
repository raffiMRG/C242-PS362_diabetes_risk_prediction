const express = require("express");
const loginHandler = require("./loginHandler");
const registerHandler = require("./registerHandler");
const refreshTokenHandler = require("./refreshTokenHandler");
const logoutHandler = require("./logoutHandler");
const accountHandler = require("./accountHandler");

const app = express();
app.use(express.json());

// Route Handlers
app.post("/login", loginHandler);
app.post("/register", registerHandler);
app.post("/logout", logoutHandler);
app.post("/refresh-token", refreshTokenHandler);
app.get("/account", accountHandler);

// Default route
app.get("/", (req, res) => {
  res.send("Welcome to the authentication API!");
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});