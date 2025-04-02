package com.example.asynctask

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.app.ComponentActivity
import com.example.asynctask.DownloadImageTask
import com.example.asynctask.R

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtUrl = findViewById<EditText>(R.id.edtUrl)
        val btnDownload = findViewById<Button>(R.id.btnDownload)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnDownload.setOnClickListener {
            val url = edtUrl.text.toString()
            if (url.isNotEmpty()) {
                val execute = DownloadImageTask(imageView, progressBar).execute(url)
            }
        }
    }
}
