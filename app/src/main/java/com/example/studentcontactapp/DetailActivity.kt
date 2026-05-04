package com.example.studentcontactapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentcontactapp.utils.FileHelper

class DetailActivity : AppCompatActivity() {

    private var studentNim: String = ""
    private var studentName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Ambil data dari Intent
        studentNim = intent.getStringExtra("STUDENT_NIM") ?: ""
        studentName = intent.getStringExtra("STUDENT_NAME") ?: ""
        val studentProdi = intent.getStringExtra("STUDENT_PRODI") ?: ""

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val tvAvatar = findViewById<TextView>(R.id.tvAvatar)
        val tvDetailName = findViewById<TextView>(R.id.tvDetailName)
        val tvDetailInfo = findViewById<TextView>(R.id.tvDetailInfo)
        val etNote = findViewById<EditText>(R.id.etNote)
        val btnSaveNote = findViewById<Button>(R.id.btnSaveNote)
        val btnLoadNote = findViewById<Button>(R.id.btnLoadNote)
        val tvNoteStatus = findViewById<TextView>(R.id.tvNoteStatus)

        // Tampilkan Data Mahasiswa
        tvDetailName.text = studentName
        tvDetailInfo.text = "$studentNim · $studentProdi"
        
        // Buat Inisial untuk Avatar
        if (studentName.isNotEmpty()) {
            val initials = studentName.split(" ")
                .mapNotNull { it.firstOrNull() }
                .take(2)
                .joinToString("")
                .uppercase()
            tvAvatar.text = initials
        }

        tvBack.setOnClickListener {
            finish()
        }

        // Fungsi pembantu untuk mengupdate teks status di bawah
        fun updateStatusText() {
            if (FileHelper.isNoteExists(this, studentNim)) {
                val size = FileHelper.getNoteSize(this, studentNim)
                tvNoteStatus.text = "✓ Tersimpan ($size bytes)"
            } else {
                tvNoteStatus.text = "Status: Belum ada catatan"
            }
        }

        // 1. Saat halaman dibuka, otomatis muat catatan jika ada
        if (FileHelper.isNoteExists(this, studentNim)) {
            val savedNote = FileHelper.loadNote(this, studentNim)
            etNote.setText(savedNote)
        }
        updateStatusText()

        // 2. Aksi Tombol Simpan
        btnSaveNote.setOnClickListener {
            val content = etNote.text.toString()
            if (studentNim.isNotEmpty()) {
                FileHelper.saveNote(this, studentNim, content)
                Toast.makeText(this, "Catatan Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                updateStatusText()
            }
        }

        // 3. Aksi Tombol Muat
        btnLoadNote.setOnClickListener {
            if (FileHelper.isNoteExists(this, studentNim)) {
                val savedNote = FileHelper.loadNote(this, studentNim)
                etNote.setText(savedNote)
                Toast.makeText(this, "Catatan Dimuat!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Belum ada catatan.", Toast.LENGTH_SHORT).show()
            }
            updateStatusText()
        }
    }
}
