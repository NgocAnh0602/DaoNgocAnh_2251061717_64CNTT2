package com.example.myfirebase

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnShowData = findViewById<Button>(R.id.btnShowData)
        val tvUserData = findViewById<TextView>(R.id.tvUserData)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUserToDatabase(email)
                            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Lỗi: ${task.exception?.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Lỗi: ${task.exception?.message}",
                                Toast.LENGTH_SHORT).show() }}
            } else {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show() } }
        btnShowData.setOnClickListener {
            val user = auth.currentUser
            if (user != null) {
                val ref = database.getReference("users").child(user.uid)
                ref.get().addOnSuccessListener { dataSnapshot ->
                    val userData = dataSnapshot.value
                    tvUserData.text = "Thông tin người dùng:\n$userData"
                }.addOnFailureListener {
                    Toast.makeText(this, "Lỗi lấy dữ liệu!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Chưa đăng nhập!", Toast.LENGTH_SHORT).show()
            }
        } }
    private fun saveUserToDatabase(email: String) {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val ref = database.getReference("users").child(userId)
            val userData = mapOf("email" to email)
            ref.setValue(userData)
        }
    }
}
