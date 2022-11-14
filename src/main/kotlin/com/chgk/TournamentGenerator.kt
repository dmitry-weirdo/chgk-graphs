package com.chgk

import com.chgk.dto.TeamQuestionsSumDto
import com.chgk.excel.ExcelParser
import com.chgk.freemarker.TournamentTemplate
import com.chgk.model.Tournament
import org.apache.logging.log4j.kotlin.Logging
import java.io.File

data class TournamentGenerator (
    val tournament: Tournament,
    val visibleTeamNames: List<String>,
    val excelParser: ExcelParser,
    val inputExcelFilePath: String,
    val htmlFileDirectory: String,
    val htmlFileName: String
) : Logging {
    val htmlFilePath: String
        get() = "$htmlFileDirectory${File.separatorChar}$htmlFileName"

    fun generateHtml() {
        parseTournament() // updates tournament fields

        val teamSums = getTeamSums(tournament)

        generateHtml(teamSums)
    }

    fun parseTournament() {
        excelParser.parseTournament(tournament, inputExcelFilePath)

        logger.info(
            """
                Tournament "${tournament.name}" parsed from file "$inputExcelFilePath".
                Total teams: ${tournament.totalTeams}
            """.trimIndent()
        )
    }

    fun getTeamSums(tournament: Tournament): List<TeamQuestionsSumDto> {
        // todo: we also need to sort by rating
        val teamsByTotalCorrectAnswersDesc = tournament
            .teams
            .sortedByDescending { it.getTotalCorrectAnswers() }

        logger.info("===========================================")
        logger.info("Teams by total results:")
        for ((index, team) in teamsByTotalCorrectAnswersDesc.withIndex()) {
            logger.info("#${index + 1} || ${team.name} || ${team.getTourResultsInOneString()}")
        }

        // write JSON file for team sums after each question
        val sums = mutableListOf<TeamQuestionsSumDto>()

        for (team in teamsByTotalCorrectAnswersDesc) {
            val teamSums = team.getSumSeries()
            if (teamSums.size != tournament.totalQuestions) {
                throw IllegalStateException("Team number ${team.tournamentNumber} (\"${team.name}\") has ${teamSums.size} result sums while the tournament has ${tournament.totalQuestions} total questions")
            }

            val sum = TeamQuestionsSumDto(team.id, team.name, team.city, team.tournamentNumber, teamSums)
            sums.add(sum)
        }

//            JacksonUtils.serializeToFile(filePath, sums, true)

        return sums
    }

    fun generateHtml(teamSums: List<TeamQuestionsSumDto>) {
        val template = TournamentTemplate()
        template.fillTemplateData(tournament, teamSums, visibleTeamNames)
        template.export(htmlFilePath)
        logger.info("Tournament \"${tournament.name}\" - graphs generated to file \"$htmlFilePath\".")
    }
}
