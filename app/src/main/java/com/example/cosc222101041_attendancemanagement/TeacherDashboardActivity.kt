

package com.example.cosc222101041_attendancemanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TeacherDashboardActivity : AppCompatActivity() {
    companion object {
        val students = ArrayList<Student>().apply {
            add(Student("muneeba", "Muneeba Sarwar"))
            add(Student("laiba", "Laiba Nadeem"))
            add(Student("rania", "Rania Ijaz"))
            add(Student("adeena", "Adeena Shakir"))
            add(Student("arwah", "Arwah Noman"))
        }
        val classes = HashMap<String, Class>()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAttendanceAdapter
    private lateinit var classSpinner: Spinner
    private lateinit var currentClass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        classSpinner = findViewById(R.id.classSpinner)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val addClass = findViewById<Button>(R.id.addClassButton)
        val addStudent = findViewById<Button>(R.id.addStudentButton)

        // Setup class spinner
        setupClassSpinner()

        // Add class button click
        addClass.setOnClickListener {
            showAddClassDialog()
        }

        // Add student button click
        addStudent.setOnClickListener {
            showAddStudentDialog()
        }

        adapter = StudentAttendanceAdapter(students)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        saveButton.setOnClickListener {
            if (this::currentClass.isInitialized && classes.containsKey(currentClass)) {
                val attendanceMap = HashMap<String, ArrayList<Boolean>>()

                adapter.getAttendanceData().forEachIndexed { index, isPresent ->
                    val studentUsername = students[index].username
                    val studentAttendance = classes[currentClass]?.attendanceRecords?.get(studentUsername) ?: ArrayList()
                    studentAttendance.add(isPresent)
                    attendanceMap[studentUsername] = studentAttendance
                }

                classes[currentClass]?.attendanceRecords?.putAll(attendanceMap)
                Toast.makeText(this, "Attendance saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClassSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classes.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classSpinner.adapter = adapter

        classSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentClass = parent?.getItemAtPosition(position).toString()
                updateAttendanceView()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun showAddClassDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_class, null)
        val classNameInput = dialogView.findViewById<EditText>(R.id.classNameInput)

        AlertDialog.Builder(this)
            .setTitle("Add New Class")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val className = classNameInput.text.toString()
                if (className.isNotEmpty()) {
                    classes[className] = Class(className)
                    setupClassSpinner()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddStudentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
        val usernameInput = dialogView.findViewById<EditText>(R.id.studentUsernameInput)
        val nameInput = dialogView.findViewById<EditText>(R.id.studentNameInput)
        val PassInput = dialogView.findViewById<EditText>(R.id.studentPassInput)

        AlertDialog.Builder(this)
            .setTitle("Add New Student")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val username = usernameInput.text.toString()
                val name = nameInput.text.toString()
                val pass = PassInput.text.toString()

                if (username.isNotEmpty() && name.isNotEmpty() && pass.isNotEmpty()) {
                    if (students.none { it.username == username }) {
                        // Add new student
                        students.add(Student(username, name , pass))

                        // Update the RecyclerView
                        adapter = StudentAttendanceAdapter(students)
                        recyclerView.adapter = adapter

                        // Initialize attendance records for all classes
                        classes.values.forEach { classData ->
                            if (!classData.attendanceRecords.containsKey(username)) {
                                classData.attendanceRecords[username] = ArrayList()
                            }
                        }

                        Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateAttendanceView() {
        val classData = classes[currentClass]?.attendanceRecords ?: HashMap()
        adapter.updateAttendanceData(classData)
    }
}