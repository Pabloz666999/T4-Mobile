package com.example.studentcontactapp.utils

import android.content.Context
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

object FileHelper {

    // 1. Menyimpan catatan ke file txt
    fun saveNote(context: Context, studentNim: String, content: String) {
        val fileName = "note_$studentNim.txt"
        // Mode private agar file hanya bisa diakses oleh aplikasi ini
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
            outputStream.write(content.toByteArray())
        }
    }

    // 2. Memuat/membaca isi catatan dari file
    fun loadNote(context: Context, studentNim: String): String {
        val fileName = "note_$studentNim.txt"
        val stringBuilder = StringBuilder()

        try {
            context.openFileInput(fileName).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line: String? = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line).append("\n")
                        line = reader.readLine()
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            // Jika file belum ada, kembalikan string kosong
            return ""
        }

        return stringBuilder.toString().trim()
    }

    // 3. Menghapus file catatan
    fun deleteNote(context: Context, studentNim: String): Boolean {
        val fileName = "note_$studentNim.txt"
        return context.deleteFile(fileName)
    }

    // 4. Mengecek apakah file catatan ada
    fun isNoteExists(context: Context, studentNim: String): Boolean {
        val fileName = "note_$studentNim.txt"
        val file = context.getFileStreamPath(fileName)
        return file.exists()
    }

    // Tambahan: Mendapatkan ukuran file (dalam bytes) untuk ditampilkan di status
    fun getNoteSize(context: Context, studentNim: String): Long {
        val fileName = "note_$studentNim.txt"
        val file = context.getFileStreamPath(fileName)
        return if (file.exists()) file.length() else 0
    }
}