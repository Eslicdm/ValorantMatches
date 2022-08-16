package com.eslirodrigues.valorantmatches.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eslirodrigues.valorantmatches.data.model.Matches
import com.eslirodrigues.valorantmatches.ui.screen.MatchesListItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MatchesRefreshList(
    matchesList: List<Matches>,
    tourName: String,
    onRefresh: () -> Unit
) {
    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { onRefresh() }
    ) {
        Text(
            text = tourName,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(10.dp),
            color = Color.White
        )
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
        ) {
            items(matchesList) { match ->
                MatchesListItem(match = match)
            }
        }
    }
}