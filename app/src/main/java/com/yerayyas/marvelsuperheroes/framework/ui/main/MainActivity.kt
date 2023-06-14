package com.yerayyas.marvelsuperheroes.framework.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.ActivityLaunchingBinding
import com.yerayyas.marvelsuperheroes.framework.ui.fragments.LaunchingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState== null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<LaunchingFragment>(R.id.fcv_main_container)
            }
        }
    }
}