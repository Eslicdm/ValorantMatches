package com.eslirodrigues.valorantmatches.di

import com.eslirodrigues.valorantmatches.data.firebase.MatchesFirebase
import com.eslirodrigues.valorantmatches.data.repository.MatchesRepository
import com.eslirodrigues.valorantmatches.data.repository.MatchesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MatchesModule {

    @Singleton
    @Provides
    fun provideFirebase() = MatchesFirebase()

    @Singleton
    @Provides
    fun provideRepository(firebase: MatchesFirebase) : MatchesRepository = MatchesRepositoryImpl(firebase)
}