package com.yerayyas.marvelsuperheroes.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.ActivityLaunchingBinding
import com.yerayyas.marvelsuperheroes.presentation.ui.fragments.LaunchingFragment
import dagger.hilt.android.AndroidEntryPoint
import android.content.res.Configuration



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            if (supportFragmentManager.findFragmentById(R.id.fcv_main_container) == null) {
                supportFragmentManager.commit {
                    add<LaunchingFragment>(R.id.fcv_main_container)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


}