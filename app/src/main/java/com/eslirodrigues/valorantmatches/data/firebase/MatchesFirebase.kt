package com.eslirodrigues.valorantmatches.data.firebase

import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.model.Matches
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MatchesFirebase {
    private val database = Firebase.database
    private val matchesReference = database.getReference("matches")

    suspend fun getAllMatches() : Result<List<Matches>> {
        val matchList = mutableListOf<Matches>()
        var errorMsg: String? = null
        matchesReference.keepSynced(true)
        matchesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.map { ds ->
                    ds.getValue(Matches::class.java)?.copy(id = ds.key!!)
                }.forEach { match ->
                    if (match != null) matchList.add(match)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                errorMsg = error.message
            }
        })
        if (matchList.isEmpty()) delay(700L)

        return withContext(Dispatchers.IO) {
            if (matchList.isNotEmpty()) {
                Result.Success(matchList)
            } else {
                Result.Error(Throwable(if (errorMsg.isNullOrEmpty()) "Not found" else errorMsg))
            }
        }
    }
}
