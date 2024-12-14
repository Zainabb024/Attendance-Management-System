package com.example.cosc222101041_attendancemanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAttendanceAdapter(private val students: ArrayList<Student>) :
    RecyclerView.Adapter<StudentAttendanceAdapter.ViewHolder>() {

    private val attendanceStatus = ArrayList<Boolean>()

    init {
        // Initialize all students as absent
        attendanceStatus.addAll(List(students.size) { false })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.studentNameTv)
        val attendanceCheckBox: CheckBox = view.findViewById(R.id.attendanceCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = students[position].name
        holder.attendanceCheckBox.isChecked = attendanceStatus[position]
        holder.attendanceCheckBox.setOnCheckedChangeListener { _, isChecked ->
            attendanceStatus[position] = isChecked
        }
    }

    override fun getItemCount() = students.size

    fun getAttendanceData(): List<Boolean> {
        return attendanceStatus.toList()
    }

    fun updateAttendanceData(data: HashMap<String, ArrayList<Boolean>>) {
        students.forEachIndexed { index, student ->
            attendanceStatus[index] = data[student.username]?.lastOrNull() ?: false
        }
        notifyDataSetChanged()
    }
}
