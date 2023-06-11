package com.yerayyas.marvelsuperheroes.framework.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import com.yerayyas.marvelsuperheroes.framework.states.MainUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loadSuperheroesUseCase: LoadSuperheroesUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    val uiState: StateFlow<MainUIState> = _uiState

    init {
        fetchSuperheroes()
    }

    private fun fetchSuperheroes() {
        viewModelScope.launch {
            try {
                loadSuperheroesUseCase.invoke().collect { superheroes ->
                    handleFetchSuccess(superheroes)
                }
            } catch (throwable: Throwable) {
                handleFetchError(throwable)
            }
        }
    }

    private fun handleFetchSuccess(superheroes: List<Superhero>) {
        _uiState.value = MainUIState.Success(superheroes)
    }


    private fun handleFetchError(throwable: Throwable) {
        val errorMessage = when (throwable) {
            is IOException -> MainUIState.Error.NetworkError("Network error. Verify your internet connection.")
            is HttpException -> {
                val code = throwable.code()
                val message = throwable.message
                MainUIState.Error.ServerError(code, "Server error. Code ($code): $message")
            }
            else -> {
                val message = throwable.message
                MainUIState.Error.UnknownError("Unknown error has occurred. $message")
            }
        }
        _uiState.value = errorMessage
    }
}

