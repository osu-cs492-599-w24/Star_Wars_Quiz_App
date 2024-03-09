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
    ): Response<SWAPICharacterList>
    @GET ("planets")
    suspend fun loadSWAPIPlanets(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SWAPIPlanetList>

    @GET("people/{id}")
    suspend fun loadSWAPICharacterDetails(
        @Path("id") uid: Int,
    ) : Response<CharacterDetails>

    @GET("planets/{id}")
    suspend fun loadSWAPIPlanetDetails(
        @Path("id") uid: Int,
    ) : Response<PlanetDetails>

    companion object {
        private const val BASE_URL = "https://www.swapi.tech/api/"
        fun create(): SWAPIService {
            val moshi = Moshi.Builder()
                .add(SWAPICharacterInfoAdapter())
                .add(SWAPIPlanetInfoAdapter())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(SWAPIService::class.java)
        }
    }
}