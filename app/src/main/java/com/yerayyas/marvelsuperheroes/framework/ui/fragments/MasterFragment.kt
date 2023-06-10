package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yerayyas.marvelsuperheroes.R
import com.yerayyas.marvelsuperheroes.databinding.FragmentMasterBinding
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.framework.states.MainUIState
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
        finishActivityOnBackPressed()
    }

    private fun finishActivityOnBackPressed() {
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
                viewModel.uiState.collect { uiState ->
                    handleUIState(uiState)
                }
            }
        }
    }

    private fun handleUIState(uiState: MainUIState) {
        when (uiState) {
            is MainUIState.Error -> {
                binding.viewLoading.isVisible = false
                showToast("An error has occurred: ${uiState.msg}")
            }

            MainUIState.Loading -> {
                binding.viewLoading.isVisible = true
            }

            is MainUIState.Success -> {
                binding.viewLoading.isVisible = false
                val data = uiState.data
                superheroesAdapter.superheroes = data
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateTo(superhero: Superhero) {

        showToast("You have pressed a card")
        val bundle = Bundle()
        bundle.putParcelable("superhero", superhero)

        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle

        parentFragmentManager.commit {
            replace<DetailFragment>(R.id.fcv_main_container)
            setReorderingAllowed(true)
            addToBackStack("principalBackStack")
        }
        //parentFragmentManager.setFragmentResult("key",bundle)
        //    val intent = Intent(this, DetailActivity::class.java).apply {
//            putExtra(DetailActivity.EXTRA_SUPERHERO, superhero)
//        }
//        startActivity(intent)
//        overridePendingTransition(
//            R.anim.auth_detail_enter,
//            R.anim.auth_detail_exit
//        )
    }

}