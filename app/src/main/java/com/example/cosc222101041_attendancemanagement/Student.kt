package com.example.cosc222101041_attendancemanagement

// Student.kt
data class Student(
    val username: String,  // for login
    val name: String,
    val password: String = "password" // default password for demo
)
