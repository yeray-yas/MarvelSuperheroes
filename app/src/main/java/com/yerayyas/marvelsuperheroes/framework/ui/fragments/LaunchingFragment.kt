package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.FragmentLaunchingBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LaunchingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


private const val ANIMATION_DURATION = 3000L

@AndroidEntryPoint
class LaunchingFragment : Fragment() {

    private lateinit var handler: Handler
    private lateinit var binding: FragmentLaunchingBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
                Log.i("YASTA", "It works")
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LaunchingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LaunchingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}