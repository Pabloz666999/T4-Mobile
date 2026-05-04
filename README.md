# Student Contact App

## Informasi Mahasiswa
- **Nama:** [MASUKKAN NAMA ANDA DI SINI]
- **NIM:** [MASUKKAN NIM ANDA DI SINI]

## Deskripsi Singkat
Aplikasi Student Contact App adalah aplikasi manajemen data mahasiswa yang memungkinkan pengguna untuk melakukan operasi CRUD (Create, Read, Update, Delete) pada data mahasiswa. Aplikasi ini juga dilengkapi dengan fitur autentikasi sederhana, pengaturan tema, dan catatan pribadi untuk setiap mahasiswa.

## Fitur Utama
- **Autentikasi:** Login dengan fitur "Remember Me" menggunakan SharedPreferences.
- **Manajemen Data (CRUD):** Menambah, melihat, mengedit, dan menghapus data mahasiswa menggunakan Room Database.
- **Pencarian:** Mencari mahasiswa berdasarkan Nama atau NIM secara real-time.
- **Catatan Pribadi:** Menyimpan catatan khusus untuk setiap mahasiswa ke Internal Storage (File .txt).
- **Pengaturan:** Fitur Dark Mode dan opsi pengaturan lainnya menggunakan SharedPreferences.
- **Swipe to Delete:** Fitur modern untuk menghapus data dengan menggeser item pada daftar.

## Screenshot Aplikasi
![Login](screenshots/login.png)
![Daftar Mahasiswa](screenshots/home.png)
![Detail & Catatan](screenshots/detail.png)
![Form Tambah](screenshots/add.png)

## Metode Penyimpanan yang Digunakan
1.  **SharedPreferences:** Digunakan untuk menyimpan session login, status "Remember Me", dan preferensi aplikasi (Dark Mode). Alasan: Cocok untuk menyimpan data sederhana berformat key-value yang bersifat ringan.
2.  **Internal Storage (File TXT):** Digunakan untuk menyimpan catatan teks pribadi mahasiswa. Alasan: Memberikan fleksibilitas dalam menyimpan konten teks yang lebih panjang dan terpisah antar entitas mahasiswa.
3.  **Room Database:** Digunakan untuk menyimpan data terstruktur mahasiswa (Nama, NIM, Prodi, dll). Alasan: Memberikan abstraksi di atas SQLite untuk manajemen database yang lebih mudah, aman (type-safe), dan performa yang baik untuk data terstruktur.

## Kendala yang Dihadapi dan Cara Mengatasinya
1.  **Masalah Kompatibilitas Metadata Kotlin:** Terdapat error saat menggunakan versi Kotlin terbaru (2.2.10) dengan Room versi lama.
    - *Solusi:* Melakukan update pada library Room ke versi 2.8.4 dan menyesuaikan versi compiler Kotlin agar sinkron.
2.  **Persyaratan compileSdk:** Beberapa library AndroidX terbaru memerlukan `compileSdk` minimal 36.
    - *Solusi:* Menaikkan `compileSdk` ke versi 37 di file `build.gradle.kts`.
3.  **Sinkronisasi UI saat Dark Mode:** Beberapa komponen tidak berubah warna secara otomatis.
    - *Solusi:* Mengimplementasikan sistem warna dinamis menggunakan `res/values/colors.xml` dan `res/values-night/colors.xml` serta mengganti warna *hardcoded* dengan referensi tema.
