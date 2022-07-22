package com.eslirodrigues.valorantmatches.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.model.Matches
import com.eslirodrigues.valorantmatches.data.repository.MatchesRepository
import com.eslirodrigues.valorantmatches.ui.state.MatchesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val repository: MatchesRepository
) : ViewModel() {

    private val _matchesState = MutableStateFlow(MatchesState())
    val matchesState: StateFlow<MatchesState> = _matchesState.asStateFlow()

    private var initialMatchesList = listOf<Matches>()

    init {
        getAllMatches()
    }

    fun searchTeams(text: String) {
        if (text.isEmpty()) {
            _matchesState.update { it.copy(isLoading = false, data = initialMatchesList) }
        } else {
            val matchesList = initialMatchesList.filter {
                it.nameTeamA.contains(text.trim(), ignoreCase = true) || it.nameTeamB.contains(text.trim(), ignoreCase = true)
            }
            _matchesState.update { it.copy(isLoading = false, data = matchesList) }
        }
    }

    fun getAllMatches() = viewModelScope.launch {
        _matchesState.update { it.copy(isLoading = true) }
        when (val result = repository.getAllMatches()) {
            is Result.Success -> {
                _matchesState.update { it.copy(isLoading = false, data = result.data) }
                initialMatchesList = result.data
            }
            is Result.Error -> {
                _matchesState.update { it.copy(isLoading = false, data = null, errorMsg = result.exception.message) }
            }
        }
    }
}