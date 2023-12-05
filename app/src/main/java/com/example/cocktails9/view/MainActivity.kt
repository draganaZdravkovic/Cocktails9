package com.example.cocktails9.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cocktails9.R
import com.example.cocktails9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var keepSplashOnScreen = true
    private val delay = 2000L
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)


        val bottomNavigationView = binding.bottomNavigationView
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miSearch -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            R.id.miFilter -> Toast.makeText(this, "Filter", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}