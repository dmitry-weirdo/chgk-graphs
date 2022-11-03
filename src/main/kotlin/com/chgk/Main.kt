package com.chgk

import com.chgk.dto.TeamQuestionsSumDto
import com.chgk.excel.XlsxParser
import com.chgk.model.Team
import com.chgk.model.Tour
import com.chgk.model.Tournament
import com.chgk.util.JacksonUtils
import org.apache.logging.log4j.kotlin.Logging

class Main : Logging {

    companion object X : Logging {
        @JvmStatic
        fun main(args: Array<String>) {
            val tournament = Tournament(
                6636,
                "Открытый чемпионат Чехии по «Что? Где? Когда?»",
                "Прага"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Гельфанд"),
                Tour(2, "Мерзляков"),
                Tour(3, "Лунёва"),
                Tour(4, "Гельфанд"),
                Tour(5, "Мерзляков"),
                Tour(6, "Скиренко / Пономарёв"),
            )

            val fileName = XlsxParser.FILE_NAME
            XlsxParser.parseTournament(tournament, fileName)

            logger.info("""
                Tournament ${tournament.name} parsed from file $fileName.
                Total teams: ${tournament.totalTeams}
            """.trimIndent())

            // aggregate operations
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

            val filePath = "c:/java/ins-new/team-sums.json"
            JacksonUtils.serializeToFile(filePath, sums, true)


            /*
                        if (false) {
                            manualTest()
                        }
            */
        }

        private fun manualTest() {
            val tournament = Tournament(
                6636,
                "Открытый чемпионат Чехии по «Что? Где? Когда?»",
                "Прага"
            )

            tournament.addTours(
                Tour(1, "Гельфанд"),
                Tour(2, "Мерзляков"),
                Tour(3, "Лунёва"),
                Tour(4, "Гельфанд"),
                Tour(5, "Мерзляков"),
                Tour(6, "Скиренко / Пономарёв"),
            )

            logger.info("""
                Tournament id: ${tournament.id} 
                Tournament name: ${tournament.name} 
                Link in old rating: ${tournament.linkInOldRating}
                Link in new rating: ${tournament.linkInNewRating}
                Tours count: ${tournament.toursCount}
                Added tours count: ${tournament.tours.size}
                Questions per tour: ${tournament.questionsPerTour}
                Total questions in the tournament: ${tournament.totalQuestions}
            """.trimIndent())

            val team = Team(
                67959,
                "Севрюга",
                null,
                15
            )

            logger.info("""
                Team id: ${team.id}
                Team name: ${team.name}
                Team city: ${team.city}
                Team has city: ${team.hasCity}
                Team is national (сборная): ${team.isNational}
                Link in old rating: ${team.linkInOldRating}
                Link in new rating: ${team.linkInNewRating}
            """.trimIndent())
        }
    }
}
