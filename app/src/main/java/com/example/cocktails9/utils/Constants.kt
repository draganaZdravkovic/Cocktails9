package com.example.cocktails9.utils

object Constants {
    const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    const val DATABASE_NAME = "favorites-database"

    const val SHARED_PREFS_NAME = "myPrefs"
    const val EMAIL_KEY = "userEmail_"
    const val PASSWORD_KEY = "userPassword_"
    const val NAME_KEY = "userName_"
    const val PHOTO_KEY = "userPhoto_"

    const val EMAIL_REGEX =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$"
    const val NAME_REGEX = "^[a-zA-Z]+$"
}