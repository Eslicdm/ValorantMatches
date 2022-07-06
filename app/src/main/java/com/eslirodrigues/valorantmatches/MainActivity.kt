package com.eslirodrigues.valorantmatches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eslirodrigues.valorantmatches.ui.screen.NavGraphs
import com.eslirodrigues.valorantmatches.ui.theme.ValorantMatchesTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValorantMatchesTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}