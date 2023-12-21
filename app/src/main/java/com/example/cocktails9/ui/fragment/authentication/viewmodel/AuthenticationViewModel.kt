package com.example.cocktails9.ui.fragment.authentication.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cocktails9.R
import com.example.cocktails9.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private var sharedPreferences: SharedPreferences
) : ViewModel() {

    private val sharedPreferencesEditor = sharedPreferences.edit()

    private val emailRegex = Regex(Constants.EMAIL_REGEX)
    private val passwordRegex = Regex(Constants.PASSWORD_REGEX)
    private val nameRegex = Regex(Constants.NAME_REGEX)

    fun getSharedPreferencesString(key: String, defaultValue: String?): String {
        return sharedPreferences.getString(key, defaultValue) ?: ""
    }

    fun addSharedPreferencesString(key: String, value: String) {
        sharedPreferencesEditor.apply{
            putString(key, value)
            apply()
        }
    }

    fun isNameValid(name: String): Boolean {
        return name.matches(nameRegex)
    }

    fun isEmailValid(email: String): Boolean {
        return email.matches(emailRegex)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.matches(passwordRegex)
    }
}