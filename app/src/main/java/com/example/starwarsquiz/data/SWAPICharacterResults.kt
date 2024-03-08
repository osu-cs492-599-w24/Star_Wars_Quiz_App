package com.example.starwarsquiz.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SWAPICharacterResults(
    val results: List<SWAPICharacter>
)
