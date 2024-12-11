# API Documentation - Diabetic

This backend API is designed to support a diabetes prediction application. The API includes various endpoints for user authentication, account management, diabetes risk prediction using machine learning (ML), and access to health articles.

## 1. Authentication (`/auth`)

This endpoint is used for user authentication.

### Routes:
- **`POST /auth/register`**: Register a new account.
- **`POST /auth/login`**: Login with user credentials.
- **`POST /auth/logout`**: Logout and remove the user session.
- **`POST /auth/refresh-token`**: Refresh the JWT token.

---

## 2. Account (`/users`)

This endpoint is used for managing user data.

### Routes:
- **`GET /users/profile`**: Retrieve user account details.
- **`PATCH /users/profile`**: Edit user account data.
- **`POST /users/profile/profile-picture`**: Upload a profile picture.
- **`PUT /users/profile/profile-picture`**: Change the profile picture.
- **`DELETE /users/profile/profile-picture`**: Delete the user's profile picture.

---

## 3. Predictions (`/predictions`)

This endpoint is used for diabetes risk prediction.

### Routes:
- **`POST /users/predictions`**: Submit data for prediction.
- **`GET /users/predictions`**: Retrieve a list of previous prediction results.

---

## 4. Articles

This endpoint provides access to articles.

### Routes:
- **`GET /articles`**: Retrieve a list of articles from Firestore.
- **`GET /articles/:id?`**: Retrieve a specific article from Firestore by ID.

---

## Security

- **JWT Authentication:** All endpoints are secured by a JWT token.
- **Role-Based Access Control:** Data access is restricted based on user authorization.

---

## Machine Learning Model Integration

The ML model is hosted on Google Cloud Storage and is used to process users' medical data in real-time.

---

## Data Storage

- **Firestore:** Stores prediction data, articles, and user information.
- **Google Cloud Storage:** Stores the ML model and files uploaded by users.

---

## Usage Instructions

To use this backend API, follow these steps:

### 1. Clone the Repository

Clone the repository to your Cloudshell:

```bash
git clone https://github.com/raffiMRG/C242-PS362_diabetes_risk_prediction.git
cd C242-PS362_diabetes_risk_prediction/CC/diabtic-backend
```
### 2. Install Dependencies

Install the required Node.js dependencies using npm:

```bash
npm install
```
### 3. Configure `app.yaml`

Ensure that the `app.yaml` configuration file is set up correctly.

```bash
runtime: nodejs18

instance_class: F2

env_variables:
  JWT_SECRET_KEY: ""
  JWT_REFRESH_SECRET_KEY: ""
  GCS_BUCKET_NAME: ""
automatic_scaling:
  target_cpu_utilization: 0.65
  max_instances: 10

handlers:
  - url: /.*
    script: auto

entrypoint: node server.js
```
### 4. Deploy to Google App Engine

Deploy this backend API to Google App Engine using the following command:

```bash
gcloud app deploy
```
This will deploy your app and make it available online. Once the deployment is complete, you will be provided with a URL where you can access your backend API.
