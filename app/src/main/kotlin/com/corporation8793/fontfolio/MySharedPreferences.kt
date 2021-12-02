package com.corporation8793.fontfolio

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("Fontfolio", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }


    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean{
        return prefs.getBoolean(key, defValue)
    }
    fun setBoolean(key: String, defValue: Boolean) {
        prefs.edit().putBoolean(key, defValue).apply()
    }

    fun getInt(key: String, value: Int): Int{
        return prefs.getInt(key, value)
    }
    fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }
}