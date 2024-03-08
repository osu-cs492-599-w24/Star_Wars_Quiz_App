package com.example.starwarsquiz.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SWAPICharacterDetailsRepository(
    private val service: SWAPIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadSWAPICharacterDetails(
        id: Int,
    ): Result<CharacterDetails?> =
        withContext(ioDispatcher) {
            try {
                val response = service.loadSWAPICharacterDetails(id)
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
