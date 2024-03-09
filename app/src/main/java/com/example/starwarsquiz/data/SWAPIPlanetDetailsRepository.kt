package com.example.starwarsquiz.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SWAPIPlanetDetailsRepository(
    private val service: SWAPIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadSWAPIPlanetDetails(
        id: Int,
    ): Result<PlanetDetails?> =
        withContext(ioDispatcher) {
            try {
                val response = service.loadSWAPIPlanetDetails(id)
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
