package com.chgk.dto

data class TeamQuestionsSumDto (
    val id: Int,
    val name: String,
    val city: String?, // todo: probably make city a separate class / enum
    val tournamentNumber: Int,
    val totalCorrectAnswers: List<Int> // after each question
)
