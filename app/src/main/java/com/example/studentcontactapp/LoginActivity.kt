package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentcontactapp.utils.PrefManager

class LoginActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi PrefManager
        prefManager = PrefManager(this)

        // Cek apakah user sudah login dan mencentang "Remember Me"
        if (prefManager.isLoggedIn() && prefManager.isRememberMe()) {
            goToMainActivity()
        }

        // Inisialisasi View dari XML
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbRememberMe = findViewById<CheckBox>(R.id.cbRememberMe)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Aksi ketika tombol Login diklik
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val isRemembered = cbRememberMe.isChecked

            // Validasi sesuai instruksi: username = admin, password = 123456
            if (username == "admin" && password == "123456") {
                // Simpan sesi login
                prefManager.saveLoginSession(username, isRemembered)

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                goToMainActivity()
            } else {
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Menutup LoginActivity agar user tidak bisa kembali ke halaman login pakai tombol 'Back'
    }
}