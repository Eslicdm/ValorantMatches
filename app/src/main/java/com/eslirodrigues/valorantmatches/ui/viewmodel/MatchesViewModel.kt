package com.eslirodrigues.valorantmatches.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.model.Matches
import com.eslirodrigues.valorantmatches.data.repository.MatchesRepository
import com.eslirodrigues.valorantmatches.ui.state.MatchesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val repository: MatchesRepository
) : ViewModel() {

    private val _matchesState = MutableStateFlow(MatchesState())
    val matchesState: StateFlow<MatchesState> = _matchesState.asStateFlow()

    private var initialMatchesList = listOf<Matches>()
    var navTourName by mutableStateOf("")

    init {
        getAllMatches()
    }

    private fun setupLastTourList() = viewModelScope.launch {
        val lastTourName = initialMatchesList.groupBy { group -> group.tourName }.keys.last()
        val lastTourList = initialMatchesList.filter { item -> item.tourName == lastTourName }
        val drawerItemList = initialMatchesList.groupBy { group -> group.tourName }.keys.toList().reversed()
        _matchesState.update {
            it.copy(
                errorMsg = null,
                isLoading = false,
                drawerItemList = drawerItemList,
                lastTourList = lastTourList,
                lastTourName = lastTourName,
                navTourList = null,
                navTourName = null
            )
        }
    }

    private fun setupNavTourWithString() = viewModelScope.launch {
        val lastTourName = initialMatchesList.groupBy { group -> group.tourName }.keys.last()
        val navTourList = initialMatchesList.filter { item -> item.tourName == navTourName }
        val drawerItemList = initialMatchesList.groupBy { group -> group.tourName }.keys.toList().reversed()
        _matchesState.update {
            it.copy(
                errorMsg = null,
                isLoading = false,
                drawerItemList = drawerItemList,
                lastTourList = null,
                lastTourName = lastTourName,
                navTourList = navTourList,
                navTourName = navTourName
            )
        }
    }

    fun searchTeams(text: String) = viewModelScope.launch {
        val lastTourName = initialMatchesList.groupBy { group -> group.tourName }.keys.last()
        val lastTourList = initialMatchesList.filter { item -> item.tourName == lastTourName }
        val navTourList = initialMatchesList.filter { item -> item.tourName == navTourName }
        if (text.isEmpty()) {
            if (navTourName.isEmpty()) {
                _matchesState.update { it.copy(lastTourList = lastTourList) }
            } else {
                _matchesState.update { it.copy(navTourList = navTourList) }
            }
        } else {
            if (navTourName.isEmpty()) {
                val lastTourListFiltered = lastTourList.filter {
                    it.nameTeamA.contains(text.trim(), ignoreCase = true) || it.nameTeamB.contains(text.trim(), ignoreCase = true)
                }
                _matchesState.update { it.copy(lastTourList = lastTourListFiltered) }
            } else {
                val navTourListFiltered = navTourList.filter {
                    it.nameTeamA.contains(text.trim(), ignoreCase = true) || it.nameTeamB.contains(text.trim(), ignoreCase = true)
                }
                _matchesState.update { it.copy(navTourList = navTourListFiltered) }
            }
        }
    }

    fun getAllMatches() = viewModelScope.launch {
        _matchesState.update { it.copy(isLoading = true) }
        when (val result = repository.getAllMatches()) {
            is Result.Success -> {
                initialMatchesList = result.data
                if (navTourName.isEmpty()) { setupLastTourList() } else { setupNavTourWithString() }
            }
            is Result.Error -> {
                _matchesState.update { it.copy(isLoading = false, errorMsg = result.exception.message) }
            }
        }
    }
}