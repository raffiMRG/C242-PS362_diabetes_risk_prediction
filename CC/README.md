# API Documentation - Diabtic
Backend API ini dibangun untuk mendukung aplikasi prediksi diabetes. API menyediakan endpoint untuk autentikasi pengguna, prediksi risiko diabetes, dan penyimpanan hasil prediksi ke Firestore.

## 1. Authentication (/account)
- Endpoint untuk kebutuhan autentikasi pengguna
- Routes :
  - `POST /register` (Daftar Akun)
  - `POST /login` (Login)
  - `POST /logout` (Logout)
  - `POST /refresh-token` (JWT)
 
## 2. Akun
- Endpoint untuk mengelola data pengguna
- Routes :
  - `GET /account`
    - Mendapatkan detail akun
  - `PATCH /account/edit`
    - Mengedit data pengguna (username, email, phone, password)
  - `POST /account/upload-profile-picture`
    - Meng upload foto profil pengguna
  - `POST /account/edit-profile-picture`
    - Mengedit foto profil pengguna
  - `DELETE /account/delete-profile-picture`
    - Menghapus foto profil pengguna
   
## 3. Prediksi
- Endpoint untuk mendapatkan prediksi diabetes dari model ML
- Routes :
  - `POST /account/predictions`
    - Mengirim data pengguna untuk di prediksi oleh model
  - `GET /account/predictions`
    - Mendapatkan hasil prediksi
   
## 4. Artikel
- Endpoint untuk mendapatkan data artikel
- Routes :
  - `GET /articles`
    - Mendapatkan data artikel dari firestore
