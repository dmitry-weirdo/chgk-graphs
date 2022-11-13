package com.chgk.freemarker

import com.chgk.dto.TeamQuestionsSumDto
import com.chgk.model.Tournament
import com.chgk.util.JacksonUtils

class TournamentTemplate : FreemarkerTemplate() {

    override val templatePath: String
        get() = "/ftl/tournament.ftl"

    fun fillTemplateData(tournament: Tournament, teamSums: List<TeamQuestionsSumDto>, visibleTeamNames: List<String>) {
        templateData["tournament"] = tournament
        templateData["teamsData"] = JacksonUtils.serializeToString(teamSums)
        templateData["tours"] = JacksonUtils.serializeToString(tournament.tours)
        templateData["visibleTeamNames"] = JacksonUtils.serializeToString(visibleTeamNames)
    }
}
