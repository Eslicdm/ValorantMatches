package com.eslirodrigues.valorantmatches.ui.state

import com.eslirodrigues.valorantmatches.data.model.Matches

data class MatchesState(
    val drawerItemList: List<String>? = null,
    val lastTourList: List<Matches>? = null,
    val lastTourName: String? = null,
    val navTourList: List<Matches>? = null,
    val navTourName: String? = null,
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)