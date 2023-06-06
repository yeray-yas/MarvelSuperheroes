package com.yerayyas.marvelsuperheroes.framework.ui.launch


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.framework.ui.fragments.LaunchingFragment


class LaunchingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launching)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<LaunchingFragment>(R.id.fcv_main_container)
        }
    }
}