package com.eslirodrigues.valorantmatches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eslirodrigues.valorantmatches.ui.navigation.NavGraph
import com.eslirodrigues.valorantmatches.ui.theme.ValorantMatchesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValorantMatchesTheme {
                NavGraph()
            }
        }
    }
}