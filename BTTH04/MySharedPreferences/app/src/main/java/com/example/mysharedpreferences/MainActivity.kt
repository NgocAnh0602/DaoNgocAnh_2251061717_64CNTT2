package com.example.mysharedpreferences

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameInput = findViewById<EditText>(R.id.etUsername)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val displayText = findViewById<TextView>(R.id.tvDisplay)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnShow = findViewById<Button>(R.id.btnShow)

        val prefHelper = PreferenceHelper(this)

        btnSave.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            prefHelper.saveData(username, password)
            displayText.text = "Dữ liệu đã được lưu!"
        }

        btnDelete.setOnClickListener {
            prefHelper.clearData()
            displayText.text = "Dữ liệu đã xóa!"
        }

        btnShow.setOnClickListener {
            val (username, password) = prefHelper.getData()
            displayText.text = "Tên: $username\nMật khẩu: $password"
        }
    }
}
