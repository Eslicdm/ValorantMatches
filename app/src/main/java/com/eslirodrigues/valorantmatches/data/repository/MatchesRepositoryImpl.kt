package com.eslirodrigues.valorantmatches.data.repository

import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.firebase.MatchesFirebase
import com.eslirodrigues.valorantmatches.data.model.Matches

class MatchesRepositoryImpl(
    private val firebase: MatchesFirebase
) : MatchesRepository {

    override suspend fun getAllMatches(): Result<List<Matches>> {
        return firebase.getAllMatches()
    }
}