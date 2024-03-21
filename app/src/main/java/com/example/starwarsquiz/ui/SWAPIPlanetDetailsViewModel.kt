package com.example.starwarsquiz.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.PlanetDetails
import com.example.starwarsquiz.data.SWAPICharacter
import com.example.starwarsquiz.data.SWAPICharacterDetailsRepository
import com.example.starwarsquiz.data.SWAPIPlanetDetailsRepository
import com.example.starwarsquiz.data.SWAPIService
import kotlinx.coroutines.launch

class SWAPIPlanetDetailsViewModel : ViewModel() {
    private val repository = SWAPIPlanetDetailsRepository(SWAPIService.create())

    private val _planetDetails = MutableLiveData<PlanetDetails?>(null)

    val planetDetails = _planetDetails

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(true)

    val loading: LiveData<Boolean> = _loading

    fun loadSWAPIPlanetDetails(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadSWAPIPlanetDetails(id)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _planetDetails.value = result.getOrNull()
            Log.d("ViewModel", "Planet Details: ${_planetDetails.value}" )
        }
    }
}