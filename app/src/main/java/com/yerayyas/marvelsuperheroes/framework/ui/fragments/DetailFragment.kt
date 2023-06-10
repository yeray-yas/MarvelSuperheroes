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


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val detailFragment = DetailFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val superhero: Superhero? = arguments?.getParcelable("DetailFragment:superhero")
        if (superhero != null) {
            //binding.toolbar.title = superhero.name
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

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun bindDetailInfo(tvDetailInfo: TextView, superhero: Superhero) {
        "${superhero.comics.available}".also { tvDetailInfo.text = it }
    }

}