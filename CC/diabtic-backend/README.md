# API Documentation - Diabtic

Backend API ini dirancang untuk mendukung aplikasi prediksi diabetes. API mencakup berbagai endpoint untuk autentikasi pengguna, pengelolaan akun, prediksi risiko diabetes menggunakan machine learning (ML), dan akses ke artikel kesehatan.

## 1. Authentication (`/account`)

Endpoint ini digunakan untuk autentikasi pengguna.

### Routes:
- **`POST /register`**: Mendaftar akun baru.
- **`POST /login`**: Login dengan kredensial pengguna.
- **`POST /logout`**: Logout dan menghapus sesi pengguna.
- **`POST /refresh-token`**: Memperbarui token JWT.

---

## 2. Akun

Endpoint ini digunakan untuk mengelola data pengguna.

### Routes:
- **`GET /account`**: Mendapatkan detail akun pengguna.
- **`PATCH /account/edit`**: Mengedit data akun pengguna.
- **`POST /account/upload-profile-picture`**: Mengunggah foto profil.
- **`POST /account/edit-profile-picture`**: Mengubah foto profil.
- **`DELETE /account/delete-profile-picture`**: Menghapus foto profil pengguna.

---

## 3. Prediksi

Endpoint ini digunakan untuk prediksi risiko diabetes.

### Routes:
- **`POST /account/predictions`**: Mengirimkan data untuk prediksi.
- **`GET /account/predictions`**: Mendapatkan daftar hasil prediksi sebelumnya.

---

## 4. Artikel

Endpoint ini memberikan akses ke artikel kesehatan.

### Routes:
- **`GET /articles`**: Mendapatkan daftar artikel kesehatan dari Firestore.

---

## Keamanan

- **JWT Authentication:** Semua endpoint dilindungi oleh token JWT.
- **Role-Based Access Control:** Akses data dibatasi sesuai otorisasi pengguna.

---

## Integrasi Model Machine Learning

Model ML di-host di Google Cloud Storage dan digunakan untuk memproses data medis pengguna secara real-time. Hasil prediksi meliputi probabilitas risiko (dalam persen) dan keputusan akhir (Yes/No).

---

## Penyimpanan Data

- **Firestore:** Menyimpan data prediksi, artikel, dan informasi pengguna.
- **Google Cloud Storage:** Menyimpan model ML dan file yang diunggah pengguna.
