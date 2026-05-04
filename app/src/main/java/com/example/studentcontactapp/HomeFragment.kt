package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.entity.StudentEntity
import com.example.studentcontactapp.utils.PrefManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var adapter: StudentAdapter
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // 1. Inisialisasi Database (Langkah 5)
        database = AppDatabase.getDatabase(requireContext())

        // 2. Inisialisasi UI
        val rvStudents = view.findViewById<RecyclerView>(R.id.rvStudents)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        val tvWelcomeHome = view.findViewById<TextView>(R.id.tvWelcomeHome)

        // Tampilkan nama user
        val prefManager = PrefManager(requireContext())
        tvWelcomeHome.text = "Welcome, ${prefManager.getUsername()}!"

        // 3. Setup RecyclerView & Adapter
        // 1: Click -> Detail, 2: Edit, 3: Delete
        adapter = StudentAdapter(emptyList(), { student ->
            // Aksi saat item diklik -> Pindah ke DetailActivity (Hands-On 2)
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("STUDENT_NIM", student.nim)
            intent.putExtra("STUDENT_NAME", student.name)
            intent.putExtra("STUDENT_PRODI", student.prodi)
            startActivity(intent)
        }, { student ->
            // Aksi saat tombol edit diklik -> Pindah ke AddStudentActivity (Langkah 6)
            val intent = Intent(requireContext(), AddStudentActivity::class.java)
            intent.putExtra("STUDENT_ID", student.id)
            startActivity(intent)
        }, { student ->
            // Aksi saat tombol delete diklik (Langkah 7)
            showDeleteDialog(student)
        })

        // Menghubungkan adapter ke RecyclerView (PENTING!)
        rvStudents.layoutManager = LinearLayoutManager(context)
        rvStudents.adapter = adapter

        // 5. Implementasi Swipe to Delete (Langkah 9)
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val student = adapter.getStudentAt(position)
                showDeleteDialog(student)
            }
        })
        itemTouchHelper.attachToRecyclerView(rvStudents)

        // 4. Tombol Tambah (FAB) - Membuka Form Tambah (Langkah 6)
        fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddStudentActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    // Refresh data setiap kali user kembali ke Fragment ini
    override fun onResume() {
        super.onResume()
        loadStudentData()
    }

    // Fungsi untuk mengambil data dari Room Database (Langkah 5)
    private fun loadStudentData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val list = database.studentDao().getAllStudents()

            // Jika database kosong, masukkan data awal otomatis
            if (list.isEmpty()) {
                val sampleData = listOf(
                    StudentEntity(name = "M. Bayu Aji", nim = "F1B021001", prodi = "Teknik Informatika", email = "bayu@unram.ac.id", semester = "6"),
                    StudentEntity(name = "Andi Wijaya", nim = "F1B021002", prodi = "Teknik Sipil", email = "andi@unram.ac.id", semester = "4")
                )
                database.studentDao().insertAll(sampleData)
                loadStudentData() // Panggil ulang untuk menampilkan data yang baru saja diinsert
            } else {
                adapter.updateData(list)
            }
        }
    }

    // Fungsi untuk menampilkan konfirmasi hapus (Langkah 7)
    private fun showDeleteDialog(student: StudentEntity) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hapus Data?")
        builder.setMessage("Hapus \"${student.name}\"? Tindakan ini tidak dapat dibatalkan.")

        builder.setPositiveButton("Hapus") { _, _ ->
            viewLifecycleOwner.lifecycleScope.launch {
                database.studentDao().deleteById(student.id)
                loadStudentData() // Muat ulang data setelah berhasil dihapus
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            loadStudentData() // Refresh untuk mengembalikan item yang ter-swipe
            dialog.dismiss()
        }

        builder.setOnCancelListener {
            loadStudentData() // Refresh jika dialog ditutup tanpa klik tombol
        }

        val alert = builder.create()
        alert.show()

        // Customize button colors after show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(android.graphics.Color.RED)
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(android.graphics.Color.GRAY)
    }
}
