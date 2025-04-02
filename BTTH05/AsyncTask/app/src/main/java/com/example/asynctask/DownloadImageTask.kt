@file:Suppress("DEPRECATION")

package com.example.asynctask

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import java.net.HttpURLConnection
import java.net.URL


class DownloadImageTask(
    private val imageView: ImageView,
    private val progressBar: ProgressBar
) : AsyncTask<String, Void, Bitmap?>() {

    override fun onPreExecute() {
        super.onPreExecute()
        progressBar.visibility = View.VISIBLE
        imageView.setImageBitmap(null)  // Xóa ảnh cũ trước khi tải ảnh mới
    }

    override fun doInBackground(vararg params: String?): Bitmap? {
        val urlString = params[0]
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        progressBar.visibility = View.GONE
        if (result != null) {
            imageView.setImageBitmap(result)
        }
    }
}
