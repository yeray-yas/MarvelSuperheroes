package com.yerayyas.marvelsuperheroes.framework.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.databinding.ViewSuperheroItemBinding
import com.yerayyas.marvelsuperheroes.domain.model.Superheroes
import com.yerayyas.marvelsuperheroes.framework.utils.common.basicDiffUtil

class SuperheroAdapter(

    private val superheroClickedListener: (Superheroes) -> Unit
) :
    RecyclerView.Adapter<SuperheroAdapter.ViewHolder>() {

    var superheroes: List<Superheroes> by basicDiffUtil(
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ViewSuperheroItemBinding.inflate(
            LayoutInflater
                .from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)

    }

    override fun getItemCount() = superheroes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val superhero = superheroes[position]
        holder.bind(superhero)
        holder.itemView.setOnClickListener {
            superheroClickedListener(superhero)
        }
    }

    class ViewHolder(private val binding: ViewSuperheroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(superhero: Superheroes) {
            binding.tvName.text = superhero.name
            val imageUrl = "${superhero.thumbnail.path}.${superhero.thumbnail.extension}"
            val context = binding.root.context
            if (imageUrl.contains("image_not_available")) {
                Glide
                    .with(context)
                    .load(R.drawable.marvel_image_not_found)
                    .into(binding.ivCover)
            } else {
                Glide.with(context)
                    .load(imageUrl)
                    .into(binding.ivCover)
            }
        }
    }
}