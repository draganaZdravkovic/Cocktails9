package com.example.cocktails9.ui.fragment.authentication.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private var sharedPreferences: SharedPreferences
) : ViewModel() {

    private val sharedPreferencesEditor = sharedPreferences.edit()

    fun getSharedPreferencesString(key: String, defaultValue: String?): String {
        return sharedPreferences.getString(key, defaultValue) ?: ""
    }

    fun addSharedPreferencesString(key: String, value: String) {
        sharedPreferencesEditor.apply{
            putString(key, value)
            apply()
        }
    }
}