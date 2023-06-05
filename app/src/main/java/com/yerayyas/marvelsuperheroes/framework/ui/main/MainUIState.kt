package com.yerayyas.marvelsuperheroes.framework.ui.main

import com.yerayyas.marvelsuperheroes.domain.model.Superhero

sealed class MainUIState {
    object Loading : MainUIState()
    data class Success(val data: List<Superhero>) : MainUIState()
    data class Error(val msg: Throwable?) : MainUIState()
}