package com.example.starwarsquiz.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.SWAPICharacter
import com.example.starwarsquiz.data.SWAPICharacterDetailsRepository
import com.example.starwarsquiz.data.SWAPIService
import kotlinx.coroutines.launch

class SWAPICharacterDetailsViewModel : ViewModel() {
    private val repository = SWAPICharacterDetailsRepository(SWAPIService.create())

    private val _characterDetails = MutableLiveData<CharacterDetails?>(null)

    val characterDetails = _characterDetails

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadSWAPICharactersDetails(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadSWAPICharacterDetails(id)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _characterDetails.value = result.getOrNull()
            Log.d("ViewModel", "Details: ${_characterDetails.value}" )
        }
    }
}