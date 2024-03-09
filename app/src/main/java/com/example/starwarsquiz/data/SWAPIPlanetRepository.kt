package com.example.starwarsquiz.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SWAPIPlanetRepository (
    private val service: SWAPIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend fun loadSWAPIPlanets(
        page: Int,
        limit: Int,
    ): Result<List<SWAPIPlanet>> =
        withContext(ioDispatcher) {
            try {
                val response = service.loadSWAPIPlanets(page, limit)
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