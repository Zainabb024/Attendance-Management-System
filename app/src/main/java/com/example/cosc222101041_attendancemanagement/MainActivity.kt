package com.example.cosc222101041_attendancemanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val teacherLoginBtn = findViewById<Button>(R.id.teacherLoginBtn)
        val studentLoginBtn = findViewById<Button>(R.id.studentLoginBtn)

        teacherLoginBtn.setOnClickListener {
            startActivity(Intent(this, TeacherLoginActivity::class.java))

        }

        studentLoginBtn.setOnClickListener {
            startActivity(Intent(this, StudentLoginActivity::class.java))
        }
    }
}