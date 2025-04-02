package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.net.Uri

class MissedCallReceiver : BroadcastReceiver() {

    companion object {
        private var lastState: String? = TelephonyManager.EXTRA_STATE_IDLE
        private var incomingNumber: String? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

        if (state == TelephonyManager.EXTRA_STATE_RINGING) {
            incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            Log.d("MissedCallReceiver", "Cuộc gọi đến từ: $incomingNumber")
        } else if (state == TelephonyManager.EXTRA_STATE_IDLE && incomingNumber != null) {
            Log.d("MissedCallReceiver", "Cuộc gọi nhỡ từ: $incomingNumber")
            sendAutoReply(context, incomingNumber!!)
        }
        lastState = state
    }

    private fun sendAutoReply(context: Context, phoneNumber: String) {
        val message = "Xin chào, tôi đang bận. Tôi sẽ gọi lại sau!"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phoneNumber") // Mở ứng dụng tin nhắn
        intent.putExtra("sms_body", message)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK // Chạy Intent ngay cả khi app đóng

        try {
            context.startActivity(intent)
            Log.d("MissedCallReceiver", "Mở ứng dụng SMS để gửi tin nhắn.")
        } catch (e: Exception) {
            Log.e("MissedCallReceiver", "Không thể mở ứng dụng SMS: ${e.message}")
        }
    }
}
