package com.example.cosc222101041_attendancemanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)

        val loginBtn = findViewById<Button>(R.id.loginButton)
        val usernameEt = findViewById<EditText>(R.id.usernameEditText)
        val passwordEt = findViewById<EditText>(R.id.passwordEditText)

        loginBtn.setOnClickListener {
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()

            val student = TeacherDashboardActivity.students.find { it.username == username }

            if (student != null && password == student.password) { // Using default password for demo
                val intent = Intent(this, StudentDashboardActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}