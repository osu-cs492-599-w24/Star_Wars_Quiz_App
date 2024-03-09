package com.example.starwarsquiz.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SWAPIPlanet(
    val uid: String,
    val name: String,
    val url: String,
)
