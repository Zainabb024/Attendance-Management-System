package com.example.cosc222101041_attendancemanagement

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class StudentDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        val username = intent.getStringExtra("username") ?: return
        val student = TeacherDashboardActivity.students.find { it.username == username }
            ?: return

        // Setup header info
        val studentNameTv = findViewById<TextView>(R.id.studentNameTv)
        val overallStatsTv = findViewById<TextView>(R.id.overallStatsTv)
        val classesContainer = findViewById<LinearLayout>(R.id.classesContainer)

        studentNameTv.text = student.name

        // Calculate overall statistics
        var totalPresent = 0
        var totalDays = 0
        TeacherDashboardActivity.classes.values.forEach { classData ->
            val attendanceList = classData.attendanceRecords[username] ?: ArrayList()
            totalPresent += attendanceList.count { it }
            totalDays += attendanceList.size
        }

        val overallPercentage = if (totalDays > 0) (totalPresent * 100.0 / totalDays) else 0.0
        overallStatsTv.text = """
            Overall Attendance
            Present: $totalPresent
            Total Classes: $totalDays
            Overall Percentage: %.2f%%
        """.trimIndent().format(overallPercentage)

        // Add individual class cards
        TeacherDashboardActivity.classes.forEach { (className, classData) ->
            val attendanceList = classData.attendanceRecords[username] ?: ArrayList()
            val presentDays = attendanceList.count { it }
            val totalClassDays = attendanceList.size
            val percentage = if (totalClassDays > 0) (presentDays * 100.0 / totalClassDays) else 0.0

            val cardView = LayoutInflater.from(this)
                .inflate(R.layout.class_attendance_card, classesContainer, false) as CardView

            cardView.findViewById<TextView>(R.id.classNameTv).text = className
            cardView.findViewById<TextView>(R.id.attendanceStatsTv).text = """
                Present: $presentDays
                Total Days: $totalClassDays
                Percentage: %.2f%%
            """.trimIndent().format(percentage)

            // Set card background color based on attendance percentage
            val backgroundColor = when {
                percentage >= 75 -> Color.parseColor("#E8F5E9") // Light Green
                percentage >= 60 -> Color.parseColor("#FFF3E0") // Light Orange
                else -> Color.parseColor("#FFEBEE") // Light Red
            }
            cardView.setCardBackgroundColor(backgroundColor)

            classesContainer.addView(cardView)
        }
    }
}