package com.saitejajanjirala.gopodcast.domain.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class PodCastResult(
    @Json(name = "audio")
    val audio: String,
    @Json(name = "audio_length_sec")
    val audioLengthSec: Int,
    @Json(name = "description_highlighted")
    val descriptionHighlighted: String,
    @Json(name = "description_original")
    val descriptionOriginal: String,
    @Json(name = "explicit_content")
    val explicitContent: Boolean,
    @Json(name = "guid_from_rss")
    val guidFromRss: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "itunes_id")
    val itunesId: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "listennotes_url")
    val listennotesUrl: String,
    @Json(name = "podcast")
    val podcast: Podcast,
    @Json(name = "pub_date_ms")
    val pubDateMs: Long,
    @Json(name = "rss")
    val rss: String,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "title_highlighted")
    val titleHighlighted: String,
    @Json(name = "title_original")
    val titleOriginal: String,
    @Json(name = "transcripts_highlighted")
    val transcriptsHighlighted: List<Any>
)