package com.yerayyas.marvelsuperheroes.framework.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yerayyas.marvelsuperheroes.data.local.SuperheroDao
import com.yerayyas.marvelsuperheroes.data.local.SuperheroEntity
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.model.Thumbnail
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import com.yerayyas.marvelsuperheroes.framework.states.Failure
import com.yerayyas.marvelsuperheroes.framework.states.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadSuperheroesUseCase: LoadSuperheroesUseCase,
    private val superheroDao: SuperheroDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<Result<List<Superhero>, Failure>>(Result.Loading)
    val uiState: StateFlow<Result<List<Superhero>, Failure>> = _uiState

    init {
        fetchSuperheroes()
    }

    private fun fetchSuperheroes() {
        viewModelScope.launch {
            try {
                val superheroesFromDb = superheroDao.getAllSuperheroes()
                if (superheroesFromDb.isNotEmpty()) {
                    handleFetchSuccess(mapSuperheroEntitiesToDomain(superheroesFromDb))
                }

                loadSuperheroesUseCase.invoke().collect { result ->
                    if (result is Result.Success) {
                        superheroDao.insertSuperheroes(mapSuperheroesToEntities(result.value))
                        handleFetchSuccess(result.value)
                    } else if (result is Result.Error) {
                        handleFetchError(result.failure)
                    }
                }
            } catch (e: Exception) {
                handleFetchError(Failure.UnknownError(e.message ?: ""))
            }
        }
    }

    private fun mapSuperheroesToEntities(superheroes: List<Superhero>): List<SuperheroEntity> {
        return superheroes.map { superhero ->
            SuperheroEntity(
                id = superhero.id,
                name = superhero.name,
                description = superhero.description,
                thumbnailUrl = "${superhero.thumbnail.extension}.${superhero.thumbnail.path}",
                comics = superhero.comics
            )
        }
    }


    private fun mapSuperheroEntitiesToDomain(superheroEntities: List<SuperheroEntity>): List<Superhero> {
        return superheroEntities.map { superheroEntity ->
            Superhero(
                id = superheroEntity.id,
                name = superheroEntity.name,
                description = superheroEntity.description,
                thumbnail = Thumbnail(
                    extension = "",
                    path = superheroEntity.thumbnailUrl
                ),
                comics = superheroEntity.comics
            )
        }
    }


    private fun handleFetchSuccess(superheroes: List<Superhero>) {
        _uiState.value = Result.Success(superheroes)
    }

    private fun handleFetchError(failure: Failure) {
        _uiState.value = Result.Error(failure)
    }
}