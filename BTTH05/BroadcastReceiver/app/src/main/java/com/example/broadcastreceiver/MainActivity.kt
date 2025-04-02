package com.example.broadcastreceiver

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.core.app.ComponentActivity

@SuppressLint("RestrictedApi")
class MainActivity : ComponentActivity() {

    private lateinit var receiver: MissedCallReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = MissedCallReceiver()
        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver) // Hủy đăng ký khi Activity bị hủy
    }
}
