package com.example.starwarsquiz.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SWAPICharacter(
    val uid: Int,
    val name: String,
    val url: String,
)
