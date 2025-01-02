package com.saitejajanjirala.gopodcast.domain.remote


import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PodCasts(
    @Json(name = "count")
    val count: Int?=0,
    @Json(name = "next_offset")
    val nextOffset: Int?=0,
    @Json(name = "results")
    val podcastResults: List<PodCastResult>?= emptyList(),
    @Json(name = "took")
    val took: Double?=0.0,
    @Json(name = "total")
    val total: Int?=0
)