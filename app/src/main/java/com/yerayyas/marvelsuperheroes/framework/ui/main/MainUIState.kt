package com.yerayyas.marvelsuperheroes.framework.ui.main

import com.yerayyas.marvelsuperheroes.data.model.Superhero

sealed class MainUIState {
    object Loading : MainUIState()
    data class Success(val data: List<Superhero>) : MainUIState()
    data class Error(val errorMessage: String) : MainUIState()
}