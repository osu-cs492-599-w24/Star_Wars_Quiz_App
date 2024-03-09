package com.example.starwarsquiz.data

import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWAPIService {
    /**
     * This method gets a list of the characters from the API. There is about 80 records here, the
     * more the limit is increased the less pages there will be. The API default is page 1 with at
     * most 10 records per page, so the same default is here.
     */
    @GET ("people")
    suspend fun loadSWAPICharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SWAPICharacterList>

    /**
     * This method gets a list of the planets from the API. There is about 60 records here, the
     * more the limit is increased the less pages there will be. The API default is page 1 with at
     * most 10 records per page, so the same default is here.
     */
    @GET ("planets")
    suspend fun loadSWAPIPlanets(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): Response<SWAPIPlanetList>

    /**
     * This method gets specific details about a character by using their uid as a path.
     */
    @GET("people/{id}")
    suspend fun loadSWAPICharacterDetails(
        @Path("id") uid: Int,
    ) : Response<CharacterDetails>

    /**
     * This method gets specific details about a planet by using its uid as a path.
     */
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