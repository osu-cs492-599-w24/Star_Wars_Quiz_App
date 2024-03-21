package com.example.starwarsquiz.data

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson


data class CharacterDetails(
    val height: Int,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val name: String,
    val homeworldId: Int,
)
@JsonClass(generateAdapter = true)
data class SWAPICharacterResultJson(
    val result: SWAPICharacterPropertiesJson
)

@JsonClass(generateAdapter = true)
data class SWAPICharacterPropertiesJson(
    val properties: SWAPICharacterDetailsJson
)

@JsonClass(generateAdapter = true)
data class SWAPICharacterDetailsJson(
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val gender: String,
    val name: String,
    val homeworld: String,
)

class SWAPICharacterInfoAdapter {
    @FromJson
    fun characterDetailsFromJson(characterDetails: SWAPICharacterResultJson) = CharacterDetails(
        height = characterDetails.result.properties.height.toInt(),
        mass = characterDetails.result.properties.mass,
        hairColor = characterDetails.result.properties.hair_color,
        skinColor = characterDetails.result.properties.skin_color,
        eyeColor = characterDetails.result.properties.eye_color,
        birthYear = characterDetails.result.properties.birth_year,
        gender = characterDetails.result.properties.gender,
        name = characterDetails.result.properties.name,
        homeworldId = parseHomeworldURL(characterDetails.result.properties.homeworld)
    )

    private fun parseHomeworldURL(url: String): Int {
        val lastIndex = url.lastIndexOf("/")

        val id = url.substring(lastIndex + 1).toInt()
        Log.d("SWAPI", "homworldID ${id}")

        return id
    }

    @ToJson
    fun characterDetailsToJson(characterDetails: CharacterDetails): String {
        throw UnsupportedOperationException("encoding CharacterDetails to JSON is not supported")
    }
}
