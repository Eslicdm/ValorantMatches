package com.eslirodrigues.valorantmatches.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.eslirodrigues.valorantmatches.R
import com.eslirodrigues.valorantmatches.ui.viewmodel.MatchesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination(start = true)
@Composable
fun MatchScreen(
    matchesViewModel: MatchesViewModel = hiltViewModel()
) {
    val matchesState by matchesViewModel.matchesState.collectAsState()
    val matchesList = matchesState.data

    val showSearchBar = remember { mutableStateOf(false) }
    val inputTextSearch = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            MatchesTopAppBar(showSearchBar, inputTextSearch, matchesViewModel)
        }
    ) {
        Column(modifier = Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = {
//                val reference = Firebase.database.getReference("matches")
//                val matchId = reference.push().key!!
//                val match = Matches(
//                    id = matchId,
//                    nameTeamA = "XIA",
//                    imgTeamA = "https://am-a.akamaihd.net/image?resize=150:150&f=http%3A%2F%2Fstatic.lolesports.com%2Fteams%2F1644381233726_XERXIAPINK.png",
//                    scoreTeamA = "0",
//                    nameTeamB = "FPX",
//                    imgTeamB = "https://am-a.akamaihd.net/image?resize=150:150&f=http%3A%2F%2Fstatic.lolesports.com%2Fteams%2F1644542485597_download38.png",
//                    scoreTeamB = "0",
//                    matchDay = "July 11",
//                    matchTime = "12 PM"
//                )
//                reference.child(matchId).setValue(match)
//            }) {
//                Text(text = "Add")
//            }
            if (matchesState.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            if (matchesList == null) {
                Text(text = if (!matchesState.errorMsg.isNullOrEmpty()) matchesState.errorMsg!! else stringResource(id = R.string.refresh))
                IconButton(onClick = { matchesViewModel.getAllMatches() }) {
                    Icon(Icons.Default.Refresh, contentDescription = stringResource(id = R.string.refresh))
                }
            }
            SwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                state = rememberSwipeRefreshState(isRefreshing = false),
                onRefresh = { matchesViewModel.getAllMatches() }
            ) {
                if (matchesList != null) {
                    LazyColumn(Modifier.fillMaxSize()) {
                        val listGroup = matchesList.groupBy { item -> item.tourName }

                        listGroup.forEach { (header, groupItems) ->
                            stickyHeader {
                                Text(
                                    text = header,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(10.dp),
                                    color = Color.White
                                )
                            }
                            items(groupItems) { match ->
                                MatchesListItem(match = match)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchesTopAppBar(
    showSearchBar: MutableState<Boolean>,
    inputTextSearch: MutableState<String>,
    matchesViewModel: MatchesViewModel
) {
    SmallTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            if (showSearchBar.value) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(8.dp),
                        value = inputTextSearch.value,
                        onValueChange = {
                            inputTextSearch.value = it
                            matchesViewModel.searchTeams(it)
                        },
                        label = {
                            Text(text = stringResource(id = R.string.search))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.search)
                            )
                        },
                        maxLines = 1,
                        singleLine = true
                    )
                    IconButton(
                        onClick = {
                            showSearchBar.value = false
                                  },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                    BackHandler {
                        showSearchBar.value = false
                    }
                }
            } else {
                IconButton(onClick = { showSearchBar.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
            }
        }
    )
}