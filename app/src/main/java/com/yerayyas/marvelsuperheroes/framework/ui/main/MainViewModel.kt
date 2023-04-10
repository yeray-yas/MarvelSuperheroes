package com.yerayyas.marvelsuperheroes.framework.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import com.yerayyas.marvelsuperheroes.framework.data.dataSources.ServerSuperheroDataSource
import com.yerayyas.marvelsuperheroes.usecases.LoadSuperheroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loadSuperheroesUseCase: LoadSuperheroesUseCase) :
    ViewModel() {

    //LIVEDATA
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _superheroes = MutableLiveData<List<Superhero>>(emptyList())
    val superheroes: LiveData<List<Superhero>> get() = _superheroes

    init {
        viewModelScope.launch {
            _loading.value = true
            //val currentSuperheroes = service.getCharacters(TS, API_KEY, HASH)
            _superheroes.value = loadSuperheroesUseCase.invoke()
            _loading.value = false
            Log.d("MainActivity", loadSuperheroesUseCase.invoke().size.toString())
        }
    }
}

/*@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}*/

