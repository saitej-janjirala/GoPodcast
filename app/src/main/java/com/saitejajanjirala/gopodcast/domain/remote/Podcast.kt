package com.saitejajanjirala.gopodcast.domain.remote


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Podcast(
    @Json(name = "id")
    val id: String?=null,
    @Json(name = "image")
    val image: String?=null,
    @Json(name = "listennotes_url")
    val listennotesUrl: String?=null,
    @Json(name = "publisher_highlighted")
    val publisherHighlighted: String?=null,
    @Json(name = "publisher_original")
    val publisherOriginal: String?=null,
    @Json(name = "thumbnail")
    val thumbnail: String?=null,
    @Json(name = "title_highlighted")
    val titleHighlighted: String?=null,
    @Json(name = "title_original")
    val titleOriginal: String?=null
) : Parcelable{

}