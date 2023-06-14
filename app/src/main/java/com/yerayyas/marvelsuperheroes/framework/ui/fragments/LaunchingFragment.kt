package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.FragmentLaunchingBinding
import dagger.hilt.android.AndroidEntryPoint




private const val ANIMATION_DURATION = 3000L

@AndroidEntryPoint
class LaunchingFragment : Fragment() {

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

        playTheAnimation()

        handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(
            {
                parentFragmentManager.commit {
                    replace<MasterFragment>(R.id.fcv_main_container)
                    setReorderingAllowed(true)
                    //addToBackStack("principal")
                }
            }, ANIMATION_DURATION
        )


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun playTheAnimation() {
        with(binding.lottieAnim) {
            setAnimation(R.raw.superheroes_animation)
            playAnimation()
        }
    }
}