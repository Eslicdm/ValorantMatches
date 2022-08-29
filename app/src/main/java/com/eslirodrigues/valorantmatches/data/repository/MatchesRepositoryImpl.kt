package com.eslirodrigues.valorantmatches.data.repository

import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.firebase.MatchesFirebase
import com.eslirodrigues.valorantmatches.data.model.Matches
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val firebase: MatchesFirebase
) : MatchesRepository {

    override suspend fun getAllMatches(): Result<List<Matches>> {
        return firebase.getAllMatches()
    }
}