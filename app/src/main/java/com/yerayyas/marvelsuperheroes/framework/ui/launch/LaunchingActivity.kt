package com.yerayyas.marvelsuperheroes.framework.ui.launch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.framework.ui.main.MainActivity


class LaunchingActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launching)


        val lottieAnimationView: LottieAnimationView = findViewById(R.id.lottieAnim)

        lottieAnimationView.setAnimation(R.raw.superheroes_animation)
        lottieAnimationView.playAnimation()


        handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.auth_detail_enter,R.anim.auth_detail_exit)
                finish()
            }, 3000
        )
    }
}