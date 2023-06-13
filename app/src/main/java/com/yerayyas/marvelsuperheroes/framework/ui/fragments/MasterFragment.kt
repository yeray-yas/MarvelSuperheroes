package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (shouldInterceptBackPress()) {
                    Toast.makeText(requireContext(), "Closing the app", Toast.LENGTH_SHORT).show()
                    activity!!.finish()
                } else {
                    isEnabled = false
                    activity?.onBackPressedDispatcher!!.onBackPressed()
                }
            }
        })

    }

    private fun shouldInterceptBackPress() = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMasterBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeUIStates()

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun setupRecyclerView() {
        binding.recyclerView.adapter = superheroesAdapter
    }

    private fun observeUIStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { result ->
                    handleResult(result)
                }
            }
        }
    }

    private fun handleResult(result: Result<List<Superhero>, Failure>) {
        with(binding.viewLoading) {
            when (result) {
                is Result.Success -> {
                    isVisible = false
                    val data = result.value
                    superheroesAdapter.superheroes = data
                }
                is Result.Error -> {
                    isVisible = false
                    when (val failure = result.failure) {
                        is Failure.NetworkError -> showToast("Network error. Verify your internet connection.")
                        is Failure.ServerError -> showToast("Server error. Code (${failure.code}): ${failure.message}")
                        is Failure.UnknownError -> showToast("Unknown error has occurred. ${failure.message}")
                    }
                }

                Result.Loading ->  Log.i("TAGG", "Ok")
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateTo(superhero: Superhero) {

        val bundle = Bundle()
        bundle.putParcelable("superhero", superhero)

        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle

        parentFragmentManager.commit {
            replace(R.id.fcv_main_container, detailFragment)
            setReorderingAllowed(true)
            addToBackStack("principalBackStack")
        }
    }
}
