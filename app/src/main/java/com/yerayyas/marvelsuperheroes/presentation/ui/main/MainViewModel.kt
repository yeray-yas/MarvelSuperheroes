package com.yerayyas.marvelsuperheroes.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.model.Super
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import com.yerayyas.marvelsuperheroes.domain.states.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.yerayyas.marvelsuperheroes.domain.states.Result
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loadSuperheroesUseCase: LoadSuperheroesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<Result<List<Super>, Failure>>(Result.Loading)
    val uiState: StateFlow<Result<List<Super>, Failure>> = _uiState

    init {
        fetchSuperheroes()
    }

    internal fun fetchSuperheroes() {
        viewModelScope.launch {
            loadSuperheroesUseCase.invoke()
                .catch { throwable -> handleFetchError(Failure.UnknownError(throwable.message ?: "")) }
                .collect { result ->
                    when (result) {
                        is Result.Success -> handleFetchSuccess(result.value)
                        is Result.Error -> handleFetchError(result.failure)
                        Result.Loading -> Log.i("TAGG", "Loading Ok")
                    }
                }
        }
    }

    internal fun handleFetchSuccess(superheroes: List<Superhero>) {
        _uiState.value = Result.Success(superheroes)
    }

    internal fun handleFetchError(failure: Failure) {
        _uiState.value = Result.Error(failure)
    }
}

