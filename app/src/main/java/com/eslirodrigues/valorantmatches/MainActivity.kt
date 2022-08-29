package com.eslirodrigues.valorantmatches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eslirodrigues.valorantmatches.ui.components.AdMobInterstitial
import com.eslirodrigues.valorantmatches.ui.navigation.NavGraph
import com.eslirodrigues.valorantmatches.ui.theme.ValorantMatchesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var adMobInterstitial: AdMobInterstitial
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adMobInterstitial.loadAd()
        setContent {
            ValorantMatchesTheme {
                NavGraph {
                    adMobInterstitial.showAdd(this)
                }
            }
        }
    }
}