package com.eslirodrigues.valorantmatches.ui.state

import com.eslirodrigues.valorantmatches.data.model.Matches

data class MatchesState(
    val data: List<Matches>? = null,
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)