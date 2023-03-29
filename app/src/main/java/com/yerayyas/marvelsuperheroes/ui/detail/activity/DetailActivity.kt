package com.yerayyas.marvelsuperheroes.ui.detail.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yerayyas.marvelsuperheroes.core.utils.inline_functions.parcelable
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SUPERHERO = "DetailActivity:superhero"

    }

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val superhero = intent.parcelable<Superhero>(EXTRA_SUPERHERO)
        if (superhero != null) {
            title = superhero.name
            Glide.with(this)
                .load("${superhero.thumbnail.path}.${superhero.thumbnail.extension}")
                .into(binding.ivBackdrop)

            if (superhero.description.isNotEmpty()){
                binding.tvDescription.text = superhero.description
            }else{
                binding.tvDescription.text = "NO DESCRIPTION FOUND\n"
            }

            bindDetailInfo(binding.tvDetailInfo, superhero)
        }
    }

    private fun bindDetailInfo(tvDetailInfo: TextView, superhero: Superhero) {
        "${superhero.comics.available}".also { tvDetailInfo.text = it }
    }
}