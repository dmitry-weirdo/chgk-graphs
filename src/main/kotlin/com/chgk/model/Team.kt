package com.chgk.model

/**
 * Команда по ЧГК.
 * @property id идентификатор команды на сайтах рейтинга
 * @property name название команды
 * @property city город, из которого команда. Если команда сборная, то <code>null</code>.
 * @property tournamentNumber номер команды в турнире
 */
data class Team (
    val id: Int,
    val name: String,
    val city: String?, // todo: probably make city a separate class / enum
    val tournamentNumber: Int,
    val tourResults: MutableList<TeamTourResults> = mutableListOf()
    // todo: отличать сборные
    // todo: состав команды
) {
    val linkInOldRating: String
        get() = "https://rating.chgk.info/team/${id}"

    val linkInNewRating: String
        get() = "https://rating.maii.li/b/team/${id}/"

    val hasCity: Boolean
        get() = (city != null)

    val isNational: Boolean // сборная // todo: better naming if possible
        get() = !hasCity

    fun addTourResult(tourResult: TeamTourResults) {
        tourResults.add(tourResult)
    }

    fun getTourResult(tourNumber: Int) = tourResults.firstOrNull { it.tourNumber == tourNumber }
}
