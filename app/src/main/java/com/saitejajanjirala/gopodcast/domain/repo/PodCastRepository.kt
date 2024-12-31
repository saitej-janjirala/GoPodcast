package com.saitejajanjirala.gopodcast.domain.repo

import com.saitejajanjirala.gopodcast.domain.remote.Genre
import com.saitejajanjirala.gopodcast.domain.util.Result
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import kotlinx.coroutines.flow.Flow

interface PodCastRepository {
    suspend fun getGenres() : Flow<Result<List<Genre>>>
    suspend fun getPodCasts(genre : String): Flow<Result<List<PodCastResult>>>
}