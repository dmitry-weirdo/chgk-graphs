package com.chgk.model

/**
 * Турнир по ЧГК.
 *
 * @property id идентификатор турнира на сайтах рейтинга
 * @property name название турнира
 * @property city город/населённый пункт, в котором происходит отыгрыш турнира
 * @property toursCount количество туров
 * @property questionsPerTour количество вопросов в туре
 */
data class Tournament (
    val id: Int,
    val name: String,
    val city: String, // todo: probably make city a separate class /  enum
    // todo: add type (Турнир, синхрон, асинхрон, usw - check the rating site API)
    // todo: add date or dates
    var toursCount: Int = 6,
    var questionsPerTour: Int = 12,
    val teams: MutableList<Team> = mutableListOf(),
    val tours: MutableList<Tour> = mutableListOf()
) {
    val linkInOldRating: String
        get() = "https://rating.chgk.info/tournament/${id}"

    val linkInNewRating: String
        get() = "https://rating.maii.li/b/tournament/${id}/"

    val totalQuestions: Int
        get() = toursCount * questionsPerTour

    fun addTeam(team: Team) = teams.add(team)

    fun addTeams(vararg teamsToAdd: Team) {
        for (team in teamsToAdd) {
            teams.add(team)
        }
    }

    fun addTeams(teamsToAdd: List<Team>) {
        for (team in teamsToAdd) {
            teams.add(team)
        }
    }

    val totalTeams: Int
        get() = teams.size

    // todo: better use maps by number and name to not iterate multiple times
    fun getTeam(teamNumber: Int): Team = teams.first { it.tournamentNumber == teamNumber }
    fun getTeam(teamName: String): Team = teams.first { it.name == teamName }

    fun addTour(tour: Tour) = tours.add(tour)

    fun addTours(vararg toursToAdd: Tour) {
        for (tour in toursToAdd) {
            tours.add(tour)
        }
    }

    val firstTourNum: Int
        get() = 1 // todo: extract to a constant

    val lastTourNum: Int
        get() = firstTourNum + toursCount - 1
}
