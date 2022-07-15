package com.eslirodrigues.valorantmatches.data.model

data class Matches(
    val id: String = "",
    val nameTeamA: String = "",
    val imgTeamA: String = "",
    val scoreTeamA: String = "",
    val nameTeamB: String = "",
    val imgTeamB: String = "",
    val scoreTeamB: String = "",
    val matchDay: String = "",
    val matchTime: String = ""
)
