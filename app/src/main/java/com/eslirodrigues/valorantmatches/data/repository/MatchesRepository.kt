package com.eslirodrigues.valorantmatches.data.repository

import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.model.Matches

interface MatchesRepository {
    suspend fun getAllMatches(): Result<List<Matches>>
}