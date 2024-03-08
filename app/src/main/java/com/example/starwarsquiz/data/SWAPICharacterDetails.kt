package com.example.starwarsquiz.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson


data class CharacterDetails(
    val height: Int,
    val mass: Int,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val name: String,
    val homeworldUrl: String,
)
@JsonClass(generateAdapter = true)
data class SWAPIResultJson(
    val result: SWAPIPropertiesJson
)

@JsonClass(generateAdapter = true)
data class SWAPIPropertiesJson(
    val properties: SWAPIDetailsJson
)

@JsonClass(generateAdapter = true)
data class SWAPIDetailsJson(
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
    fun characterDetailsFromJson(characterDetails: SWAPIResultJson) = CharacterDetails(
        height = characterDetails.result.properties.height.toInt(),
        mass = characterDetails.result.properties.mass.toInt(),
        hairColor = characterDetails.result.properties.hair_color,
        skinColor = characterDetails.result.properties.skin_color,
        eyeColor = characterDetails.result.properties.eye_color,
        birthYear = characterDetails.result.properties.birth_year,
        gender = characterDetails.result.properties.gender,
        name = characterDetails.result.properties.name,
        homeworldUrl = characterDetails.result.properties.homeworld
    )

    @ToJson
    fun characterDetailsToJson(characterDetails: CharacterDetails): String {
        throw UnsupportedOperationException("encoding CharacterDetails to JSON is not supported")
    }
}
