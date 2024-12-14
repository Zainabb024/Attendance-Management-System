package com.example.cosc222101041_attendancemanagement

// Class.kt
data class Class(
    val name: String,
    val attendanceRecords: HashMap<String, ArrayList<Boolean>> = HashMap() // student username -> attendance
)