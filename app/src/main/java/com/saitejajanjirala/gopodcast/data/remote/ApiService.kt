package com.saitejajanjirala.gopodcast.data.remote

import com.saitejajanjirala.gopodcast.domain.remote.GenreResult
import com.saitejajanjirala.gopodcast.domain.remote.PodCasts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val BASE_URL = "https://listen-api.listennotes.com/api/v2/"
        const val TEST_BASE_URL = "https://listen-api-test.listennotes.com/api/v2/"
    }
    @GET("search")
    suspend fun getPodCasts(@Query("q") q : String): Response<PodCasts>

    @GET("genres")
    suspend fun getGenres(@Query("top_level_only") topLevelOnly :Int= 1) : Response<GenreResult>
}