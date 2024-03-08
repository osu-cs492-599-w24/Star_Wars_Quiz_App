package com.example.starwarsquiz.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SWAPICharacter(
    val uid: String,
    val name: String,
    val url: String,
)
