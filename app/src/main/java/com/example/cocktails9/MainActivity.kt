package com.example.cocktails9

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var keepSplashOnScreen = true
    private val delay = 2000L

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.cocktailsFragment,
                R.id.favoritesFragment,
                R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfig)

        bottomNavigationView.setupWithNavController(navController)
    }
}