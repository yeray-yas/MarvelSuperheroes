package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.FragmentDetailBinding
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
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
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val superhero: Superhero? = arguments?.getParcelable("superhero")
        if (superhero != null) {
            binding.toolbar.title = superhero.name
            Glide.with(this)
                .load("${superhero.thumbnail.path}.${superhero.thumbnail.extension}")
                .into(binding.ivBackdrop)

            if (superhero.description.isNotEmpty()){
                binding.tvDescription.text = superhero.description
            }else{
                binding.tvDescription.text = getString(R.string.no_description_found_text)
            }

            bindDetailInfo(binding.tvDetailInfo, superhero)
        }

        return binding.root
    }

    private fun bindDetailInfo(tvDetailInfo: TextView, superhero: Superhero) {
        "${superhero.comics.available}".also { tvDetailInfo.text = it }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}