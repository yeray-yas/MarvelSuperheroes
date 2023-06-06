package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.ActivityLaunchingBinding
import com.yerayyas.marvelsuperheroes.databinding.FragmentLaunchingBinding


class LaunchingFragment : Fragment(R.layout.fragment_launching) {

    private lateinit var handler: Handler
    private lateinit var binding: FragmentLaunchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLaunchingBinding.inflate(inflater, container, false)

        playAnimation()

        handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(
            {
                Log.i("YASTA", "It works")
                Toast.makeText(context, "Let's go to the second Fragment", Toast.LENGTH_SHORT).show()
                parentFragmentManager.commit {
                    replace<HomeFragment>(R.id.fcv_main_container)
                    setReorderingAllowed(true)
                    //addToBackStack("principal")
                }
            }, 3000
        )



        // Inflate the layout for this fragment
        return binding.root
    }



    private fun playAnimation() {
        with(binding.lottieAnim){
            setAnimation(R.raw.superheroes_animation)
            playAnimation()
        }
    }
}