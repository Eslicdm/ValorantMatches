package com.eslirodrigues.valorantmatches.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eslirodrigues.valorantmatches.R
import com.eslirodrigues.valorantmatches.ui.viewmodel.MatchesViewModel

@Composable
fun MatchesTopAppBar(
    showSearchBar: MutableState<Boolean>,
    inputTextSearch: MutableState<String>,
    matchesViewModel: MatchesViewModel,
    navigationIcon: @Composable () -> Unit = {}
) {
    SmallTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            navigationIcon()
        },
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