package com.example.starwarsquiz.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SWAPIService {
    @GET ("people")
    suspend fun loadCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SWAPICharacterResults>

    companion object {
        private const val BASE_URL = "https://www.swapi.tech/api/"
        fun create(): SWAPIService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SWAPIService::class.java)
        }
    }
}