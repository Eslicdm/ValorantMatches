package com.eslirodrigues.valorantmatches.data.repository

import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.model.Matches
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getAllMatches(): Flow<Result<List<Matches>>>
}