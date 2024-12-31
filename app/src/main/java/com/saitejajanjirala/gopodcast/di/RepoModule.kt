package com.saitejajanjirala.gopodcast.di

import com.saitejajanjirala.gopodcast.data.repo.PodCastRepositoryImpl
import com.saitejajanjirala.gopodcast.domain.repo.PodCastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    
    @Binds
    @Singleton
    abstract fun bindPodCastRepository(podCastRepositoryImpl: PodCastRepositoryImpl): PodCastRepository

}