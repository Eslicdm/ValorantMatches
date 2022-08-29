package com.eslirodrigues.valorantmatches

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MatchesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
        Firebase.messaging.subscribeToTopic("valorantmatches")
        MobileAds.initialize(this)
    }
}