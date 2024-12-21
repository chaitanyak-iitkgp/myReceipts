package com.chaitanyakhiratkar.myreceipts.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils(context: Context , name : String) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun editSharedPreferenceData(key: String, value: Any) {
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IllegalArgumentException("Unsupported data type")
        }
        editor.apply()
    }

    fun getSharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    inline fun <reified T> getSharedPreferenceData(key: String, defaultValue: T): T {
        return when (T::class) {
            String::class -> getSharedPreferences().getString(key, defaultValue as String) as T
            Int::class -> getSharedPreferences().getInt(key, defaultValue as Int) as T
            Boolean::class -> getSharedPreferences().getBoolean(key, defaultValue as Boolean) as T
            Float::class -> getSharedPreferences().getFloat(key, defaultValue as Float) as T
            Long::class -> getSharedPreferences().getLong(key, defaultValue as Long) as T
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }
}