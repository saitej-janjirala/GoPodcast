package com.saitejajanjirala.gopodcast.domain.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResult(
    @Json(name = "genres")
    val genres: List<Genre>? = null
)