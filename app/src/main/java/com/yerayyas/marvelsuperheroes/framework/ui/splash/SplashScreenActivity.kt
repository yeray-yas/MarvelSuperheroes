package com.yerayyas.marvelsuperheroes.framework.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.framework.ui.main.MainActivity


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash= installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        screenSplash.setKeepOnScreenCondition{false}
        startActivity(Intent(this@SplashScreenActivity,
            MainActivity::class.java))
        finish()

    }
}