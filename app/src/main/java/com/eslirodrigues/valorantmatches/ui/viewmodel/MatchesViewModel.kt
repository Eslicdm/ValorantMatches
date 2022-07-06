package com.eslirodrigues.valorantmatches.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.model.Matches
import com.eslirodrigues.valorantmatches.data.repository.MatchesRepository
import com.eslirodrigues.valorantmatches.ui.state.MatchesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val repository: MatchesRepository
) : ViewModel() {

    private val _matchesState = MutableStateFlow(MatchesState())
    val matchesState: StateFlow<MatchesState> = _matchesState.asStateFlow()

    private var matchesList = listOf<Matches>()
    private var matchesListImmutable = listOf<Matches>()
    private var cachedMatchesList = listOf<Matches>()
    var isSearchStarting = true

    init {
        getAllMatches()
    }

    fun searchFruitList(query: String) {
        val listToSearch = if(isSearchStarting) {
            matchesList = matchesListImmutable
            matchesList
        } else {
            cachedMatchesList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                matchesList = matchesListImmutable
                _matchesState.update { it.copy(isLoading = false, data = matchesList) }
                return@launch
            }
            val results = listToSearch.filter {
                it.nameTeamA.contains(query.trim(), ignoreCase = true) ||
                        it.nameTeamB.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                cachedMatchesList = matchesList
                isSearchStarting = false
            }
            matchesList = results
            _matchesState.update { it.copy(isLoading = false, data = matchesList) }
        }
    }

    fun getAllMatches() = viewModelScope.launch {
        _matchesState.update { it.copy(isLoading = true) }
        when (val result = repository.getAllMatches()) {
            is Result.Success -> {
                _matchesState.update { it.copy(isLoading = false, data = result.data) }
                matchesListImmutable = result.data
            }
            is Result.Error -> {
                _matchesState.update { it.copy(isLoading = false, data = null, errorMsg = result.exception.message) }
            }
        }
    }
}