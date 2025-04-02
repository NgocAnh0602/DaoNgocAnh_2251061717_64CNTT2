package com.example.mysqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val tvDisplay = findViewById<TextView>(R.id.tvDisplay)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnShow = findViewById<Button>(R.id.btnShow)

        val dbHelper = DatabaseHelper(this)

        btnAdd.setOnClickListener {
            val success = dbHelper.addContact(etName.text.toString(), etPhone.text.toString())
            Toast.makeText(this, if (success) "Thêm thành công" else "Thêm thất bại",
                Toast.LENGTH_SHORT).show()
        }

        btnUpdate.setOnClickListener {
            val success = dbHelper.updateContact(etName.text.toString(), etPhone.text.toString())
            Toast.makeText(this, if (success) "Sửa thành công" else "Sửa thất bại",
                Toast.LENGTH_SHORT).show()
        }

        btnDelete.setOnClickListener {
            val success = dbHelper.deleteContact(etName.text.toString())
            Toast.makeText(this, if (success) "Xóa thành công" else "Xóa thất bại",
                Toast.LENGTH_SHORT).show()
        }

        btnShow.setOnClickListener {
            tvDisplay.text = dbHelper.getAllContacts()
        }
    }
}
