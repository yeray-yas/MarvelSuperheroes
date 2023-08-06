package com.yerayyas.marvelsuperheroes.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.FragmentMasterBinding
import com.yerayyas.marvelsuperheroes.domain.model.Super
import com.yerayyas.marvelsuperheroes.domain.states.Failure
import com.yerayyas.marvelsuperheroes.domain.states.Result
import com.yerayyas.marvelsuperheroes.presentation.ui.main.MainAdapter
import com.yerayyas.marvelsuperheroes.presentation.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MasterFragment : Fragment() {

    private lateinit var binding: FragmentMasterBinding
    private val viewModel by viewModels<MainViewModel>()
    private val superheroesAdapter by lazy { MainAdapter(::navigateTo) }
    private var dataList: List<Super> = emptyList()
    private var isDataLoaded: Boolean = false

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUIStates()

        if (savedInstanceState != null && dataList.isEmpty()) {
            savedInstanceState.getParcelableArrayList<Super>("dataList")?.let { savedDataList ->
                dataList = savedDataList
                isDataLoaded = true
                superheroesAdapter.superheroes = dataList
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = superheroesAdapter
    }

    private fun observeUIStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        // Mostrar carga o progreso
                    }
                    is Result.Success -> {
                        val superheroes = result.value
                        // Actualizar el adaptador con los datos
                    }
                    is Result.Error -> {
                        val failure = result.failure
                        // Mostrar el error en la UI
                    }
                }
            }
        }

    }

    private fun handleResult(result: Result<List<Super>, Failure>) {
        with(binding.viewLoading) {
            isVisible = result is Result.Loading

            if (result is Result.Success) {
                dataList = result.value
                isDataLoaded = true
                superheroesAdapter.superheroes = dataList
                superheroesAdapter.notifyDataSetChanged()
            } else if (result is Result.Error) {
                when (val failure = result.failure) {
                    is Failure.NetworkError -> {
                        showToast("Network error. Verify your internet connection.")
                        isDataLoaded = false
                    }
                    is Failure.ServerError -> {
                        showToast("Server error. Code (${failure.code}): ${failure.message}")
                        isDataLoaded = false
                    }
                    is Failure.UnknownError -> {
                        showToast("Unknown error has occurred. ${failure.message}")
                        isDataLoaded = false
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateTo(superhero: Super) {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (dataList.isNotEmpty()) {
            outState.putParcelableArrayList("dataList", ArrayList(dataList))
        }
    }
}

