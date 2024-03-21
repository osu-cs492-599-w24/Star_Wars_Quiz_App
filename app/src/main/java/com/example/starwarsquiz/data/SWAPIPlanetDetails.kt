package com.example.starwarsquiz.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson


data class PlanetDetails(
    val diameter: String,
    val rotationPeriod: String,
    val orbitalPeriod: String,
    val gravity: String,
    val population: String,
    val climate: String,
    val terrain: String,
    val surfaceWater: String,
    val name: String,
)
@JsonClass(generateAdapter = true)
data class SWAPIPlanetIResultJson(
    val result: SWAPIPlanetPropertiesJson
)

@JsonClass(generateAdapter = true)
data class SWAPIPlanetPropertiesJson(
    val properties: SWAPIPlanetDetailsJson
)

@JsonClass(generateAdapter = true)
data class SWAPIPlanetDetailsJson(
    val diameter: String,
    val rotation_period: String,
    val orbital_period: String,
    val gravity: String,
    val population: String,
    val climate: String,
    val terrain: String,
    val surface_water: String,
    val name: String,
)

class SWAPIPlanetInfoAdapter {
    @FromJson
    fun planetDetailsFromJson(planetDetails: SWAPIPlanetIResultJson) = PlanetDetails(
        diameter = planetDetails.result.properties.diameter,
        rotationPeriod = planetDetails.result.properties.rotation_period,
        orbitalPeriod = planetDetails.result.properties.orbital_period,
        gravity = planetDetails.result.properties.gravity,
        population = planetDetails.result.properties.population,
        climate = planetDetails.result.properties.climate,
        terrain = planetDetails.result.properties.terrain,
        surfaceWater = planetDetails.result.properties.surface_water,
        name = planetDetails.result.properties.name,
    )

    @ToJson
    fun planetDetailsToJson(planetDetails: PlanetDetails): String {
        throw UnsupportedOperationException("encoding PlanetDetails to JSON is not supported")
    }
}
