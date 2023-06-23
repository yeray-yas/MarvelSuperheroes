package com.yerayyas.marvelsuperheroes.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.FragmentDetailBinding
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.model.Super
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val superhero: Super? = arguments?.getParcelable("superhero")
        superhero?.let { showSuperheroDetails(it) }

        return binding.root
    }

    private fun showSuperheroDetails(superhero: Super) {
        binding.toolbar.title = superhero.name

        val imageUrl = "${superhero.thumbnail.path}.${superhero.thumbnail.extension}"

        if (imageUrl.contains("image_not_available")) {
            // Utilizar una imagen personalizada cuando la imagen recuperada es la imagen genérica
            Glide.with(this)
                .load(R.drawable.marvel_image_not_found)
                .into(binding.ivBackdrop)
        } else {
            Glide.with(this)
                .load(imageUrl)
                .into(binding.ivBackdrop)
        }

        if (superhero.description.isNotEmpty()) {
            binding.tvDescription.text = superhero.description
        } else {
            binding.tvDescription.text = getString(R.string.no_description_found_text)
        }

        bindDetailInfo(binding.tvDetailInfo, superhero)
    }

    private fun bindDetailInfo(tvDetailInfo: TextView, superhero: Super) {
        tvDetailInfo.text = superhero.comics.available.toString()
    }
}

