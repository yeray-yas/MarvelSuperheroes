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
    private val viewModel by activityViewModels<MainViewModel>()
    private val superheroesAdapter by lazy { MainAdapter(::navigateTo) }
    private var dataList: List<Super> = emptyList()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUIStates()

        if (savedInstanceState != null && dataList.isEmpty()) {
            savedInstanceState.getParcelableArrayList<Super>("dataList")?.let { savedDataList ->
                dataList = savedDataList
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
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: Result<List<Super>, Failure>) {
        with(binding.viewLoading) {
            isVisible = result is Result.Loading

            if (result is Result.Success) {
                dataList = result.value
                superheroesAdapter.superheroes = dataList
            } else if (result is Result.Error) {
                when (val failure = result.failure) {
                    is Failure.NetworkError -> {
                        showToast("Network error. Verify your internet connection.")
                    }
                    is Failure.ServerError -> showToast("Server error. Code (${failure.code}): ${failure.message}")
                    is Failure.UnknownError -> showToast("Unknown error has occurred. ${failure.message}")
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // No se necesita implementación aquí
    }
}

