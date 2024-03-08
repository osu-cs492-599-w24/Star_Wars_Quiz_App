package com.example.starwarsquiz.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SWAPICharactersRepository (
    private val service: SWAPIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend fun loadSWAPICharacters(
        page: Int,
        limit: Int,
    ): Result<List<SWAPICharacter>> =
        withContext(ioDispatcher) {
            try {
                val response = service.loadSWAPICharacters(page, limit)
                if (response.isSuccessful) {
                    Log.d("Repository", "Characters ${response.body()}")
                    Result.success(response.body()?.results ?: listOf())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}