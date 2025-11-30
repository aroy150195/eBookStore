package com.aroy.ebookstore.core

import android.content.Context

/**
 * Created by Amit Roy on Date : 01/12/25
 */
class SharePrefsHelper(context: Context) {
    private val prefs = context.getSharedPreferences("eBookStorePrefs", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, default: String = ""): String {
        return prefs.getString(key, default) ?: default
    }

    fun saveInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, default: Int = 0): Int {
        return prefs.getInt(key, default)
    }

    fun saveBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }

    fun saveFloat(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return prefs.getFloat(key, default)
    }

    fun saveDouble(key: String, value: Double) {
        // SharedPreferences doesn’t support Double directly → store as String
        prefs.edit().putString(key, value.toString()).apply()
    }

    fun getDouble(key: String, default: Double = 0.0): Double {
        return prefs.getString(key, default.toString())?.toDoubleOrNull() ?: default
    }
}