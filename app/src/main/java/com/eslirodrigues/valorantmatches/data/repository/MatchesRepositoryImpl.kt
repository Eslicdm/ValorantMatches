package com.eslirodrigues.valorantmatches.data.repository

import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.firebase.MatchesFirebase
import com.eslirodrigues.valorantmatches.data.model.Matches
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val firebase: MatchesFirebase
) : MatchesRepository {

    override suspend fun getAllMatches(): Flow<Result<List<Matches>>> {
        return firebase.getAllMatches()
    }
}