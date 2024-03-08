package com.example.starwarsquiz.data

import android.util.Log
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
                    Log.d("Repository", "This Details ${response.body()}")
                    Result.success(response.body())
                } else {
                    Log.e("Repository", "Details Error: ${response.errorBody()?.string()}")
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Log.e("Repository", "Catch Details Error", e)
                Result.failure(e)
            }
        }
}
