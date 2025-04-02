package com.example.mytime

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity

@SuppressLint("RestrictedApi")
class MainActivity : ComponentActivity() {

    private lateinit var timerTextView: TextView
    private var timeElapsed = 0  // Số giây đã trôi qua
    private val handler = Handler(Looper.getMainLooper()) // Handler để cập nhật UI
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)

        runnable = object : Runnable {
            override fun run() {
                timeElapsed++
                timerTextView.text = "Thời gian: $timeElapsed giây"
                handler.postDelayed(this, 1000) // Lặp lại sau 1 giây
            }
        }

        handler.post(runnable) // Bắt đầu đếm giờ
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Dừng Runnable khi Activity bị hủy
    }
}
