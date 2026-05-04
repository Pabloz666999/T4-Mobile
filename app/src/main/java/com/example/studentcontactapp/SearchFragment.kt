package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.database.AppDatabase
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var adapter: StudentAdapter
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        database = AppDatabase.getDatabase(requireContext())

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val rvSearchResults = view.findViewById<RecyclerView>(R.id.rvSearchResults)

        adapter = StudentAdapter(emptyList(), { student ->
            // Klik item -> Detail
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("STUDENT_NIM", student.nim)
            intent.putExtra("STUDENT_NAME", student.name)
            intent.putExtra("STUDENT_PRODI", student.prodi)
            startActivity(intent)
        }, { student ->
            // Tombol edit -> Form Edit
            val intent = Intent(requireContext(), AddStudentActivity::class.java)
            intent.putExtra("STUDENT_ID", student.id)
            startActivity(intent)
        }, { student ->
            // Bisa tambahkan delete di sini juga jika mau
            // Untuk sekarang biarkan kosong atau tambahkan fungsi delete
        })

        rvSearchResults.layoutManager = LinearLayoutManager(context)
        rvSearchResults.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchData(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Tampilkan semua data di awal
        searchData("")

        return view
    }

    private fun searchData(keyword: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            val results = if (keyword.isEmpty()) {
                database.studentDao().getAllStudents()
            } else {
                database.studentDao().searchStudents(keyword)
            }
            adapter.updateData(results)
        }
    }
}
