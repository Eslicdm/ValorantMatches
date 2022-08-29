package com.eslirodrigues.valorantmatches.di

import android.content.Context
import com.eslirodrigues.valorantmatches.data.firebase.MatchesFirebase
import com.eslirodrigues.valorantmatches.data.repository.MatchesRepository
import com.eslirodrigues.valorantmatches.data.repository.MatchesRepositoryImpl
import com.eslirodrigues.valorantmatches.ui.components.AdMobInterstitial
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MatchesModule {

    @Singleton
    @Provides
    fun provideFirebase() : MatchesFirebase = MatchesFirebase()

    @Singleton
    @Provides
    fun provideRepository(firebase: MatchesFirebase) : MatchesRepository = MatchesRepositoryImpl(firebase)

    @Singleton
    @Provides
    fun provideAdMobInterstitial(@ApplicationContext context: Context) : AdMobInterstitial = AdMobInterstitial(context)
}