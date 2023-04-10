package com.yerayyas.marvelsuperheroes.framework.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.yerayyas.marvelsuperheroes.framework.ui.common.Constants.API_KEY
import com.yerayyas.marvelsuperheroes.framework.ui.common.Constants.HASH
import com.yerayyas.marvelsuperheroes.framework.ui.common.Constants.TS
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.network.SuperheroDbClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //LIVEDATA
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _superheroes = MutableLiveData<List<Superhero>>(emptyList())
    val superheroes: LiveData<List<Superhero>> get() = _superheroes

    init {
        viewModelScope.launch {
            _loading.value = true
            val currentSuperheroes =
                SuperheroDbClient
                    .service
                    .getCharacters(TS, API_KEY, HASH)


            _superheroes.value = currentSuperheroes.data.superheroes
            _loading.value = false
            Log.d("MainActivity", currentSuperheroes.data.superheroes.size.toString())


        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}

