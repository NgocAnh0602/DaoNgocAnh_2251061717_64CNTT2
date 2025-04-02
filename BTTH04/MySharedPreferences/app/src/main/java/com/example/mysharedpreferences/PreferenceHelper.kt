package com.example.mysharedpreferences

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveData(username: String, password: String) {
        sharedPref.edit().apply {
            putString("USERNAME", username)
            putString("PASSWORD", password)
            apply()
        }
    }

    fun getData(): Pair<String?, String?> {
        val username = sharedPref.getString("USERNAME", "")
        val password = sharedPref.getString("PASSWORD", "")
        return Pair(username, password)
    }

    fun clearData() {
        sharedPref.edit().clear().apply()
    }
}
