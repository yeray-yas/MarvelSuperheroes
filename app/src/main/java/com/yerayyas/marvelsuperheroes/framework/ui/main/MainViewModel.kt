package com.yerayyas.marvelsuperheroes.framework.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
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

    fun onCreate() {
        viewModelScope.launch {
            _loading.value = true
            val result = loadSuperheroesUseCase.invoke()

            if (!result.isNullOrEmpty()){
                _superheroes.value = result
                _loading.value = false
                Log.d("MainActivity", loadSuperheroesUseCase.invoke().size.toString())
            }

        }
    }
}