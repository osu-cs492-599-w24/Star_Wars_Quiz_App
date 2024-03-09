package com.example.starwarsquiz.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SWAPIPlanetList(
    val results: List<SWAPIPlanet>
)