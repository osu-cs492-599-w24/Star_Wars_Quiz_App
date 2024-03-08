package com.example.starwarsquiz.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.scalars.ScalarsConverterFactory

interface SWAPIService {
    @GET ("people")
    fun loadCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Call<String>

    companion object {
        private const val BASE_URL = "https://www.swapi.tech/api/"
        fun create(): SWAPIService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(SWAPIService::class.java)
        }
    }
}