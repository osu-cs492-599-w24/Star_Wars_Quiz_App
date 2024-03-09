package com.example.starwarsquiz.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SWAPICharacterList(
    val results: List<SWAPICharacter>
)
