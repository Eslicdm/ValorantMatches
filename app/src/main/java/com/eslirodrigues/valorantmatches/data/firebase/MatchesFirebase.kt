package com.eslirodrigues.valorantmatches.data.firebase

import com.eslirodrigues.valorantmatches.data.Result
import com.eslirodrigues.valorantmatches.data.model.Matches
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MatchesFirebase {
    private val database = Firebase.database
    private val matchesReference = database.getReference("matches")

    suspend fun getAllMatches() : Flow<Result<List<Matches>>> = callbackFlow {
        trySend(Result.Loading)
        matchesReference.keepSynced(true)
        val event = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val matches = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(Matches::class.java)
                }
                trySend(Result.Success(matches))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.Error(Throwable("Not Found")))
            }
        }
        matchesReference.addValueEventListener(event)
        awaitClose { close() }
    }
}
