package com.example.studentcontactapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.entity.StudentEntity
import kotlinx.coroutines.launch

class AddStudentActivity : AppCompatActivity() {

    private var studentId: Int = 0
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Inisialisasi Database
        val db = AppDatabase.getDatabase(this)

        // Inisialisasi View
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val etName = findViewById<EditText>(R.id.etName)
        val etNim = findViewById<EditText>(R.id.etNim)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSemester = findViewById<EditText>(R.id.etSemester)
        val spProdi = findViewById<Spinner>(R.id.spProdi)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // Listener Tombol Back
        tvTitle.setOnClickListener {
            finish()
        }

        // Ambil data dari intent jika ada (untuk Mode Edit)
        studentId = intent.getIntExtra("STUDENT_ID", 0)
        if (studentId != 0) {
            isEditMode = true
            tvTitle.text = "← Edit Data Mahasiswa"
            btnSave.text = "Update Data"
            
            // Ambil data lama dari database
            lifecycleScope.launch {
                val student = db.studentDao().getStudentById(studentId)
                student?.let {
                    etName.setText(it.name)
                    etNim.setText(it.nim)
                    etEmail.setText(it.email)
                    etSemester.setText(it.semester)
                    
                    // Set Spinner Prodi
                    val prodiArray = resources.getStringArray(R.array.prodi_array)
                    val index = prodiArray.indexOf(it.prodi)
                    if (index >= 0) spProdi.setSelection(index)
                }
            }
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val nim = etNim.text.toString()
            val email = etEmail.text.toString()
            val semester = etSemester.text.toString()
            val prodi = spProdi.selectedItem.toString()

            if (name.isEmpty() || nim.isEmpty() || semester.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi Nama, NIM, dan Semester!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (isEditMode) {
                    // Mode Edit: Update data
                    val student = StudentEntity(
                        id = studentId,
                        name = name,
                        nim = nim,
                        prodi = prodi,
                        email = email,
                        semester = semester
                    )
                    db.studentDao().update(student)
                    Toast.makeText(this@AddStudentActivity, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
                } else {
                    // Mode Tambah: Insert data baru
                    val student = StudentEntity(
                        name = name,
                        nim = nim,
                        prodi = prodi,
                        email = email,
                        semester = semester
                    )
                    db.studentDao().insert(student)
                    Toast.makeText(this@AddStudentActivity, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
}
