package com.eslirodrigues.valorantmatches.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eslirodrigues.valorantmatches.R
import com.eslirodrigues.valorantmatches.data.model.Matches

@Composable
fun MatchesListItem(match: Matches) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = match.matchDay)
            Text(text = match.matchTime)
        }
        AsyncImage(model = match.imgTeamA, contentDescription = match.nameTeamA)
        Text(text = "${match.scoreTeamA} - ${match.scoreTeamB}")
        AsyncImage(model = match.imgTeamB, contentDescription = match.nameTeamB)
    }
}