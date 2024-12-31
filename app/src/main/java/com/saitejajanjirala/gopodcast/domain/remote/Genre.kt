package com.saitejajanjirala.gopodcast.domain.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "parent_id")
    val parentId: Int? = null,
    var isSelected : Boolean = false
)