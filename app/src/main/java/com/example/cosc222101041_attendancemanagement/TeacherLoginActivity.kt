package com.example.cosc222101041_attendancemanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TeacherLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_login)

        val loginBtn = findViewById<Button>(R.id.loginButton)
        val usernameEt = findViewById<EditText>(R.id.usernameEditText)
        val passwordEt = findViewById<EditText>(R.id.passwordEditText)

        loginBtn.setOnClickListener {
            // Dummy login check
            if (usernameEt.text.toString() == "teacher" && passwordEt.text.toString() == "password") {
                startActivity(Intent(this, TeacherDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}