package com.example.starwarsquiz.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsquiz.data.SWAPICharacter
import com.example.starwarsquiz.data.SWAPICharactersRepository
import com.example.starwarsquiz.data.SWAPIPlanet
import com.example.starwarsquiz.data.SWAPIPlanetRepository
import com.example.starwarsquiz.data.SWAPIService
import kotlinx.coroutines.launch

class SWAPIPlanetViewModel : ViewModel() {
    private val repository = SWAPIPlanetRepository(SWAPIService.create())

    private val _planetList = MutableLiveData<List<SWAPIPlanet>?>(null)

    val planetList = _planetList

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadSWAPIPlanets(page: Int, limit: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadSWAPIPlanets(page, limit)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _planetList.value = result.getOrNull()
            Log.d("ViewModel", "Character: ${_planetList.value}" )
        }
    }

}