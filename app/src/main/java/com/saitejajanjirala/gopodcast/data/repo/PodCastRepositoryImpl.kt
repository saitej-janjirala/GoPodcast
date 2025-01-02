package com.saitejajanjirala.gopodcast.data.repo

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.saitejajanjirala.gopodcast.data.remote.ApiService
import com.saitejajanjirala.gopodcast.di.NoInternetException
import com.saitejajanjirala.gopodcast.domain.remote.Genre
import com.saitejajanjirala.gopodcast.domain.repo.PodCastRepository
import com.saitejajanjirala.gopodcast.domain.util.Result
import com.saitejajanjirala.gopodcast.domain.remote.PodCastResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PodCastRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context,
                                                private val apiService: ApiService) : PodCastRepository{
    override suspend fun getGenres(): Flow<Result<List<Genre>>> =flow{
        emit(Result.Loading(true))
        val res = apiService.getGenres()
        emit(Result.Loading(false))
        if(res.isSuccessful){
            res.body()?.let {
                it.genres?.let {
                    emit(Result.Success(it))
                }
            }?:emit(Result.Error("No Data Found"))
        }
        else{
            emit(Result.Error("Unknown Error Occurred"))
        }
    }.catch { e->
        emit(Result.Loading(false))
        getErrorFlow<List<Genre>>(e)
    }

    override suspend fun getPodCasts(genre: String): Flow<Result<List<PodCastResult>>> =flow{
        emit(Result.Loading(true))
        val res = apiService.getPodCasts(genre)
        emit(Result.Loading(false))
        if(res.isSuccessful) {
            val podcasts = res.body()?.podcastResults ?: emptyList()
            emit(Result.Success(podcasts))
        }
        else{
            emit(Result.Error("Unknown Error Occurred"))
        }
    }.catch { e->
        emit(Result.Loading(false))
        getErrorFlow<List<Genre>>(e)
    }

    override  fun getExoplayer(): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    private fun <T>getErrorFlow(e:Throwable):Flow<Result<T>> = flow{
        when(e){
            is NoInternetException->{
                emit(Result.Error(e.message?:"No Internet Available"))
            }
            else->{
                emit((Result.Error("Unknown Error Occured")))
            }
        }
    }
}