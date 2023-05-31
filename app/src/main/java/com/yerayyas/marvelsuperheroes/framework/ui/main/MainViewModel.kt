package com.yerayyas.marvelsuperheroes.framework.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loadSuperheroesUseCase: LoadSuperheroesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    val uiState: StateFlow<MainUIState> = _uiState

    private val _superheroes = MutableStateFlow<List<Superhero>>(emptyList())
    val superheroes: StateFlow<List<Superhero>> = _superheroes

    init {
        fetchSuperheroes()
    }

    private fun fetchSuperheroes() {
        viewModelScope.launch {
            loadSuperheroesUseCase.invoke()
                .catch { throwable ->
                    handleFetchError(throwable)
                }
                .collect { superheroes ->
                    handleFetchSuccess(superheroes)
                }

        }
    }

    private fun handleFetchSuccess(superheroes: List<Superhero>) {
        _superheroes.value = superheroes
        _uiState.value = MainUIState.Success(superheroes)
    }

    private fun handleFetchError(throwable: Throwable) {
        val errorMessage = throwable.cause
        _uiState.value = MainUIState.Error(errorMessage)
    }
}