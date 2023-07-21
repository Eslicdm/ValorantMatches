package com.eslirodrigues.valorantmatches.ui.state

import com.eslirodrigues.valorantmatches.data.model.Matches

data class MatchesState(
    val drawerItemList: List<String> = emptyList(),
    val lastTourList: List<Matches> = emptyList(),
    val lastTourName: String = "",
    val navTourList: List<Matches> = emptyList(),
    val navTourName: String = "",
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)