package com.yerayyas.marvelsuperheroes.framework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.yerayyas.marvelsuperheroes.framework.states.MainUIState
import com.yerayyas.marvelsuperheroes.framework.ui.main.MainViewModel
import com.yerayyas.marvelsuperheroes.framework.ui.main.SuperheroAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MasterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

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
                viewModel.uiState.collect { uiState ->
                    handleUIState(uiState)
                }
            }
        }
    }

    private fun handleUIState(uiState: MainUIState) {
        with(binding.viewLoading){
            when (uiState) {
                is MainUIState.Error.NetworkError -> {
                    isVisible = false
                    showToast(uiState.message)
                }
                is MainUIState.Error.ServerError -> {
                    isVisible = false
                    showToast("${uiState.message}, ${uiState.code}")
                }
                is MainUIState.Error.UnknownError -> {
                    isVisible = false
                    showToast(uiState.message)
                }
                MainUIState.Loading -> {
                    isVisible = true
                }
                is MainUIState.Success -> {
                   isVisible = false
                    val data = uiState.data
                    superheroesAdapter.superheroes = data
                }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MasterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MasterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}