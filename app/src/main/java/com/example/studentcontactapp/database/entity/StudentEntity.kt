package com.example.studentcontactapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Menandai class ini sebagai tabel database dengan nama "students"
@Entity(tableName = "students")
data class StudentEntity(
    // Menentukan Primary Key yang akan bertambah otomatis (Auto Increment)
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val nim: String,
    val prodi: String,
    val email: String,
    val semester: String,

    // Menyimpan waktu data dibuat dalam format milidetik
    val createdAt: Long = System.currentTimeMillis()
)