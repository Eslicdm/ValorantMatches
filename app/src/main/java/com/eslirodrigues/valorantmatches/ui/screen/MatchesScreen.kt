package com.eslirodrigues.valorantmatches.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.eslirodrigues.valorantmatches.R
import com.eslirodrigues.valorantmatches.data.model.Matches
import com.eslirodrigues.valorantmatches.ui.components.*
import com.eslirodrigues.valorantmatches.ui.navigation.NavRoute
import com.eslirodrigues.valorantmatches.ui.viewmodel.MatchesViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(
    navController: NavController,
    matchesViewModel: MatchesViewModel = hiltViewModel()
) {
    val matchesState by matchesViewModel.matchesState.collectAsStateWithLifecycle()

    val showSearchBar = remember { mutableStateOf(false) }
    val inputTextSearch = remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    NavDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                itemsList = matchesState.drawerItemList,
                currentItem = matchesState.lastTourName
            ) { navItem ->
                when {
                    navItem != matchesState.lastTourName -> navController.navigate(
                        NavRoute.TourScreen.withArgs(
                            navItem,
                            matchesState.lastTourName
                        )
                    )
                }
                scope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MatchesTopAppBar(
                    showSearchBar = showSearchBar,
                    inputTextSearch =  inputTextSearch,
                    matchesViewModel = matchesViewModel,
                    navigationIcon =  {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = { AdMobBanner() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//            Button(onClick = {
//                val reference = Firebase.database.getReference("matches")
//                val matchId = reference.push().key!!
//                val match = Matches(
//                    id = matchId,
//                    nameTeamA = "FNC",
//                    imgTeamA = "https://am-a.akamaihd.net/image?resize=150:&f=http%3A%2F%2Fstatic.lolesports.com%2Fteams%2F1644542427486_download37.png",
//                    scoreTeamA = "3",
//                    nameTeamB = "EG",
//                    imgTeamB = "https://am-a.akamaihd.net/image?resize=150:&f=http%3A%2F%2Fstatic.lolesports.com%2Fteams%2F1644568669110_CrestWhite1.png",
//                    scoreTeamB = "0",
//                    matchDay = "Jun 25",
//                    matchTime = "1 AM",
//                    tourName = "Master Tokyo 2023"
//                )
//                reference.child(matchId).setValue(match)
//            }) {
//                Text(text = "Add")
//            }
                when {
                    matchesState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    matchesState.lastTourList.isEmpty() && !showSearchBar.value -> {
                        Text(text = matchesState.errorMsg ?: stringResource(id = R.string.refresh))
                        IconButton(onClick = { matchesViewModel.getAllMatches() }) {
                            Icon(Icons.Default.Refresh, contentDescription = stringResource(id = R.string.refresh))
                        }
                    }
                    matchesState.lastTourList.isNotEmpty() -> {
                        MatchesRefreshList(
                            tourName = matchesState.lastTourName,
                            matchesList = matchesState.lastTourList,
                            onRefresh = { matchesViewModel.getAllMatches() }
                        )
                    }
                }
            }
        }
    }
}
