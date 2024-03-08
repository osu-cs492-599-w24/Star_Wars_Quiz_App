package com.example.starwarsquiz.data

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
                val response = service.loadCharacters(page, limit)
                if (response.isSuccessful) {
                    Result.success(response.body()?.results ?: listOf())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}