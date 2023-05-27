package com.yerayyas.marvelsuperheroes.framework.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.databinding.ActivityMainBinding
import com.yerayyas.marvelsuperheroes.framework.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        collectUIStates()
        viewModel.onCreate()
    }


    private fun collectUIStates() {
        val superheroesAdapter = SuperheroAdapter { superhero ->
            navigateTo(superhero)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is MainUIState.Error -> {
                            binding.progress.isVisible = false
                            Toast.makeText(
                                this@MainActivity,
                                "Ha ocurrido un error: ${uiState.errorMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        MainUIState.Loading -> {
                            binding.progress.isVisible = true
                        }

                        is MainUIState.Success -> {
                            binding.progress.isVisible = false
                            val data = uiState.data
                            superheroesAdapter.superheroes = data
                        }
                    }
                }
            }
        }
        binding.recyclerView.adapter = superheroesAdapter
    }

    private fun navigateTo(superhero: Superhero) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_SUPERHERO, superhero)
        startActivity(intent)
        overridePendingTransition(R.anim.auth_detail_enter, R.anim.auth_detail_exit)
    }
}