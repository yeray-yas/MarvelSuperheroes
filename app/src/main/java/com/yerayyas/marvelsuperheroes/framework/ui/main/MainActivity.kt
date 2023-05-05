package com.yerayyas.marvelsuperheroes.framework.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.databinding.ActivityMainBinding
import com.yerayyas.marvelsuperheroes.framework.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val superheroesAdapter = SuperheroAdapter { superhero ->
            navigateTo(superhero)

        }

        binding.recyclerView.adapter = superheroesAdapter

        viewModel.onCreate()

        viewModel.loading.observe(this) { visible ->
            binding.progress.visibility = if (visible) View.VISIBLE else View.GONE
        }

        viewModel.superheroes.observe(this) { superheroes ->
            superheroesAdapter.superheroes = superheroes
        }
    }

    private fun navigateTo(superhero: Superhero) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_SUPERHERO, superhero)
        startActivity(intent)
    }
}