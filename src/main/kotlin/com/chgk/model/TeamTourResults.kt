package com.chgk.model

data class TeamTourResults(
    val tourNumber: Int,
    val teamName: String,
    val teamNumber: Int,
    val questionsAnswered: List<Boolean> // in order of questions in the tour
) {
    val totalCorrectAnswers: Int // count only correct answers
        get() = questionsAnswered.count { it }
}
