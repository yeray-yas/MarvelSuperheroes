package com.yerayyas.marvelsuperheroes.framework.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loadSuperheroesUseCase: LoadSuperheroesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    val uiState: StateFlow<MainUIState> = _uiState

    private val _superheroes = MutableStateFlow<List<Superhero>>(emptyList())
    val superheroes: StateFlow<List<Superhero>> = _superheroes

    fun onCreate() {
        viewModelScope.launch {
            loadSuperheroesUseCase.invoke()
                .catch { _uiState.value = MainUIState.Error(it.message.orEmpty()) }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiState.value = MainUIState.Success(it)
                }
        }
    }
}