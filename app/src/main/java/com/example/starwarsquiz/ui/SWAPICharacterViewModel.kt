package com.example.starwarsquiz.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsquiz.data.SWAPICharacter
import com.example.starwarsquiz.data.SWAPICharactersRepository
import com.example.starwarsquiz.data.SWAPIService
import kotlinx.coroutines.launch

class SWAPICharacterViewModel : ViewModel() {
    private val repository = SWAPICharactersRepository(SWAPIService.create())

    private val _characterList = MutableLiveData<List<SWAPICharacter>?>(null)

    val characterResults = _characterList

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadSWAPICharacters(page: Int, limit: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadSWAPICharacters(page, limit)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _characterList.value = result.getOrNull()
            Log.d("ViewModel", "Character List: ${_characterList.value}" )
        }
    }

}