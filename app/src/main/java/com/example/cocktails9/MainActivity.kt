package com.example.cocktails9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setOnExitAnimationListener {
                splashScreenView ->
            splashScreenView.iconView
                .animate()
                .setDuration(
                    3000L
                )
                .alpha(0f)
                .withEndAction {
                    splashScreenView.remove()
                }.start()
        }
        setContentView(R.layout.activity_main)
    }
}