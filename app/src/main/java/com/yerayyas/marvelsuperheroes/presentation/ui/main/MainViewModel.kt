package com.yerayyas.marvelsuperheroes.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yerayyas.marvelsuperheroes.domain.model.Super
import com.yerayyas.marvelsuperheroes.domain.states.Failure
import com.yerayyas.marvelsuperheroes.domain.states.Result
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loadSuperheroesUseCase: LoadSuperheroesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<Result<List<Super>, Failure>>(Result.Loading)
    val uiState: StateFlow<Result<List<Super>, Failure>> get() = _uiState.asStateFlow()

    private var dataLoaded = false

    init {
        fetchSuperheroes()
    }

    fun fetchSuperheroes() {
        viewModelScope.launch {
            _uiState.value = Result.Loading

            try {
                val result = loadSuperheroesUseCase.invoke()
                if (result is Result.Success) {
                    dataLoaded = true
                    _uiState.value = result
                } else {
                    _uiState.value = Result.Error(result.failure)
                }
            } catch (e: Exception) {
                _uiState.value = Result.Error(Failure.UnknownError(e.message))
            }
        }
    }
    fun fetchSuperheroes() {
        viewModelScope.launch {
            _uiState.value = Result.Loading

            try {
                val result = loadSuperheroesUseCase.invoke()
                when (result) {
                    is Result.Success -> {
                        dataLoaded = true
                        _uiState.value = result
                    }
                    is Result.Error -> _uiState.value = result
                }
            } catch (e: Exception) {
                _uiState.value = Result.Error(Failure.UnknownError(e.message))
            }
        }
    }




    fun refreshData() {
        if (!dataLoaded) {
            fetchSuperheroes()
        }
    }
}
