# API Documentation - Diabtic
Backend API ini dibangun untuk mendukung aplikasi prediksi diabetes. API menyediakan endpoint untuk autentikasi pengguna, prediksi risiko diabetes, dan penyimpanan hasil prediksi ke Firestore.

## 1. Authentication (/account)
- Endpoint untuk kebutuhan autentikasi pengguna, seperti mendaftar akun dan login ke aplikasi
- Routes :
  - POST /account/register (Daftar Akun)
  - POST /account/login (Login)
