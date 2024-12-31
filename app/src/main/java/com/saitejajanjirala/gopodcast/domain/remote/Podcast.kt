package com.saitejajanjirala.gopodcast.domain.remote


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Podcast(
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "listennotes_url")
    val listennotesUrl: String,
    @Json(name = "publisher_highlighted")
    val publisherHighlighted: String,
    @Json(name = "publisher_original")
    val publisherOriginal: String,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "title_highlighted")
    val titleHighlighted: String,
    @Json(name = "title_original")
    val titleOriginal: String
) : Parcelable{

}