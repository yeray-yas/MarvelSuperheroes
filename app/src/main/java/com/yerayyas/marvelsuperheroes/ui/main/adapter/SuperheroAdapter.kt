package com.yerayyas.marvelsuperheroes.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.databinding.ViewSuperheroItemBinding

class SuperheroAdapter(
    var superheroes: List<Superhero>,
    private val superheroClickedListener: (Superhero) -> Unit
) :
    RecyclerView.Adapter<SuperheroAdapter.ViewHolder>() {


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

        fun bind(superhero: Superhero) {
            binding.tvName.text = superhero.name

            Glide
                .with(binding.root.context)
                .load("${superhero.thumbnail.path}.${superhero.thumbnail.extension}")
                .into(binding.ivCover)

        }
    }
}