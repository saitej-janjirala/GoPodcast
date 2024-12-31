package com.saitejajanjirala.gopodcast.domain.remote


import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PodCasts(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next_offset")
    val nextOffset: Int,
    @Json(name = "results")
    val podcastResults: List<PodCastResult>,
    @Json(name = "took")
    val took: Double,
    @Json(name = "total")
    val total: Int
)