package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yerayyas.marvelsuperheroes.framework.states.Result
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.FragmentMasterBinding
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.model.Superheroes
import com.yerayyas.marvelsuperheroes.framework.states.Failure
import com.yerayyas.marvelsuperheroes.framework.ui.main.MainViewModel
import com.yerayyas.marvelsuperheroes.framework.ui.main.SuperheroAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MasterFragment : Fragment() {

    private lateinit var binding: FragmentMasterBinding
    private val viewModel by viewModels<MainViewModel>()
    private val superheroesAdapter by lazy { SuperheroAdapter(::navigateTo) }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (shouldInterceptBackPress()) {
                requireActivity().finish()
            } else {
                isEnabled = false
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun shouldInterceptBackPress() = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMasterBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeUIStates()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = superheroesAdapter
    }

    private fun observeUIStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { result ->
                    handleResult(result)
                }
            }
        }
    }

    private fun handleResult(result: Result<List<Superheroes>, Failure>) {
        with(binding.viewLoading) {
            isVisible = result is Result.Loading

            if (result is Result.Success) {
                superheroesAdapter.superheroes = result.value
            } else if (result is Result.Error) {
                when (val failure = result.failure) {
                    is Failure.NetworkError -> showToast("Network error. Verify your internet connection.")
                    is Failure.ServerError -> showToast("Server error. Code (${failure.code}): ${failure.message}")
                    is Failure.UnknownError -> showToast("Unknown error has occurred. ${failure.message}")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateTo(superhero: Superheroes) {
        val bundle = Bundle().apply {
            putParcelable("superhero", superhero)
        }

        val detailFragment = DetailFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.commit {
            setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit)

            replace(R.id.fcv_main_container, detailFragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}



