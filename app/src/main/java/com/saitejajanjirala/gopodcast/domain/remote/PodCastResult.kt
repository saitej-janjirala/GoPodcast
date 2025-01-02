package com.saitejajanjirala.gopodcast.domain.remote


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PodCastResult(
    @Json(name = "audio")
    val audio: String?=null,
    @Json(name = "audio_length_sec")
    val audioLengthSec: Int?=0,
    @Json(name = "description_highlighted")
    val descriptionHighlighted: String?=null,
    @Json(name = "description_original")
    val descriptionOriginal: String?=null,
    @Json(name = "explicit_content")
    val explicitContent: Boolean?=false,
    @Json(name = "guid_from_rss")
    val guidFromRss: String?=null,
    @Json(name = "id")
    val id: String?=null,
    @Json(name = "image")
    val image: String?=null,
    @Json(name = "itunes_id")
    val itunesId: Int?=0,
    @Json(name = "link")
    val link: String?=null,
    @Json(name = "listennotes_url")
    val listennotesUrl: String?=null,
    @Json(name = "podcast")
    val podcast: Podcast?=null,
    @Json(name = "pub_date_ms")
    val pubDateMs: Long?=0L,
    @Json(name = "rss")
    val rss: String?=null,
    @Json(name = "thumbnail")
    val thumbnail: String?=null,
    @Json(name = "title_highlighted")
    val titleHighlighted: String?=null,
    @Json(name = "title_original")
    val titleOriginal: String?=null,
):Parcelable