package com.saitejajanjirala.gopodcast.di

import android.content.Context
import com.saitejajanjirala.gopodcast.data.remote.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(@ApplicationContext context: Context):OkHttpClient{
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("X-ListenAPI-Key","4ae1c508cc794a63bfca9b686947bef3")
                .build()

            chain.proceed(newRequest)
        }
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(InternetInterceptor(context))// Add the interceptor here
            .build()
        return okHttpClient
    }

    @Provides
    @Singleton
    fun providesApiService(okHttpClient: OkHttpClient) : ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiService.TEST_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            ))
            .build()
            .create(ApiService::class.java)
    }


}