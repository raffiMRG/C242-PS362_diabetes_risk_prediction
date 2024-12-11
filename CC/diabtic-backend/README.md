# API Documentation - Diabtic

Backend API ini dirancang untuk mendukung aplikasi prediksi diabetes. API mencakup berbagai endpoint untuk autentikasi pengguna, pengelolaan akun, prediksi risiko diabetes menggunakan machine learning (ML), dan akses ke artikel kesehatan.

## 1. Authentication (`/auth`)

Endpoint ini digunakan untuk autentikasi pengguna.

### Routes:
- **`POST /auth/register`**: Mendaftar akun baru.
- **`POST /auth/login`**: Login dengan kredensial pengguna.
- **`POST /auth/logout`**: Logout dan menghapus sesi pengguna.
- **`POST /auth/refresh-token`**: Memperbarui token JWT.

---

## 2. Akun (`/users`)

Endpoint ini digunakan untuk mengelola data pengguna.

### Routes:
- **`GET /users/profile`**: Mendapatkan detail akun pengguna.
- **`PATCH /users/profile`**: Mengedit data akun pengguna.
- **`POST /users/profile/profile-picture`**: Mengunggah foto profil.
- **`PUT /users/profile/profile-picture`**: Mengubah foto profil.
- **`DELETE /users/profile/profile-picture`**: Menghapus foto profil pengguna.

---

## 3. Prediksi (/predictions)

Endpoint ini digunakan untuk prediksi risiko diabetes.

### Routes:
- **`POST /users/predictions`**: Mengirimkan data untuk prediksi.
- **`GET /users/predictions`**: Mendapatkan daftar hasil prediksi sebelumnya.

---

## 4. Artikel

Endpoint ini memberikan akses ke artikel.

### Routes:
- **`GET /articles`**: Mendapatkan daftar artikel dari Firestore.
- **`GET /articles/:id?`**: Mendapatkan daftar artikel dari Firestore berdasarkan ID.

---

## Keamanan

- **JWT Authentication:** Semua endpoint dilindungi oleh token JWT.
- **Role-Based Access Control:** Akses data dibatasi sesuai otorisasi pengguna.

---

## Integrasi Model Machine Learning

Model ML di-host di Google Cloud Storage dan digunakan untuk memproses data medis pengguna secara real-time.

---

## Penyimpanan Data

- **Firestore:** Menyimpan data prediksi, artikel, dan informasi pengguna.
- **Google Cloud Storage:** Menyimpan model ML dan file yang diunggah pengguna.
