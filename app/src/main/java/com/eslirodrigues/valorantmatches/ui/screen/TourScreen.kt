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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eslirodrigues.valorantmatches.R
import com.eslirodrigues.valorantmatches.ui.components.DrawerContent
import com.eslirodrigues.valorantmatches.ui.components.MatchesRefreshList
import com.eslirodrigues.valorantmatches.ui.components.MatchesTopAppBar
import com.eslirodrigues.valorantmatches.ui.components.NavDrawer
import com.eslirodrigues.valorantmatches.ui.navigation.NavRoute
import com.eslirodrigues.valorantmatches.ui.viewmodel.MatchesViewModel
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TourScreen(
    navController: NavController,
    tourName: String,
    lastTourName: String,
    matchesViewModel: MatchesViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        matchesViewModel.navTourName = tourName
    }

    val navTourState by matchesViewModel.matchesState.collectAsState()

    val navTourList = navTourState.navTourList ?: emptyList()

    val showSearchBar = remember { mutableStateOf(false) }
    val inputTextSearch = remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItemList = navTourState.drawerItemList ?: emptyList()

    NavDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                itemsList = drawerItemList,
                currentItem = tourName,
                onItemClick = { navItem ->
                    when {
                        navItem != lastTourName && navItem != tourName-> navController.navigate(NavRoute.TourScreen.withArgs(navItem, lastTourName))
                        navItem == lastTourName -> navController.navigate(NavRoute.MatchesScreen.route)
                        navItem == tourName -> scope.launch { drawerState.close() }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                MatchesTopAppBar(
                    showSearchBar = showSearchBar,
                    inputTextSearch =  inputTextSearch,
                    matchesViewModel = matchesViewModel,
                    navigationIcon =  {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        }
                    }
                )
            },
            bottomBar = {
                AndroidView(factory = {
                    AdView(it).apply {
                        setAdSize(AdSize.FLUID)
                    }
                })
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when {
                    navTourState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    navTourList.isEmpty() && !navTourState.isLoading && !showSearchBar.value -> {
                        Text(text = navTourState.errorMsg ?: stringResource(id = R.string.refresh))
                        IconButton(onClick = { matchesViewModel.getAllMatches() }) {
                            Icon(Icons.Default.Refresh, contentDescription = stringResource(id = R.string.refresh))
                        }
                    }
                    navTourList.isNotEmpty() -> {
                        MatchesRefreshList(
                            tourName = tourName,
                            matchesList = navTourList,
                            onRefresh = { matchesViewModel.getAllMatches() }
                        )
                    }
                }
            }
        }
    }
}