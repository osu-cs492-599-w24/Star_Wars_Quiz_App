package com.example.starwarsquiz.data

import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWAPIService {
    @GET ("people")
    suspend fun loadSWAPICharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SWAPICharacterResults>

    @GET("people/{id}")
    suspend fun loadSWAPICharacterDetails(
        @Path("id") uid: Int,
    ) : Response<CharacterDetails>

    companion object {
        private const val BASE_URL = "https://www.swapi.tech/api/"
        fun create(): SWAPIService {
            val moshi = Moshi.Builder()
                .add(SWAPICharacterInfoAdapter())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SWAPIService::class.java)
        }
    }
}