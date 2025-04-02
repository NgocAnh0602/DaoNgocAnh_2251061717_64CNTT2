package com.example.mysensor

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat.getSystemService
import kotlin.math.roundToInt

@SuppressLint("RestrictedApi")
class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private lateinit var tvAccelerometer: TextView
    private lateinit var ball: ImageView

    private var xPos = 0f
    private var yPos = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAccelerometer = findViewById(R.id.tvAccelerometer)
        ball = findViewById(R.id.ball)

        // Lấy SensorManager từ hệ thống
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0] // Gia tốc trục X
            val y = it.values[1] // Gia tốc trục Y
            val z = it.values[2] // Gia tốc trục Z

            // Hiển thị giá trị gia tốc
            tvAccelerometer.text = "Gia tốc: X=${x.roundToInt()}, Y=${y.roundToInt()}, Z=${z.roundToInt()}"

            // Tính toán vị trí mới của quả bóng
            xPos -= x * 5  // Nhân 5 để di chuyển nhanh hơn
            yPos += y * 5

            // Giới hạn biên để quả bóng không vượt khỏi màn hình
            val maxX = (ball.parent as? FrameLayout)?.width?.toFloat() ?: 0f
            val maxY = (ball.parent as? FrameLayout)?.height?.toFloat() ?: 0f

            if (xPos < 0) xPos = 0f
            if (yPos < 0) yPos = 0f
            if (xPos > maxX - ball.width) xPos = maxX - ball.width
            if (yPos > maxY - ball.height) yPos = maxY - ball.height

            // Cập nhật vị trí của quả bóng
            ball.translationX = xPos
            ball.translationY = yPos
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Không cần xử lý trong ứng dụng này
    }
}
