package com.chgk

import com.chgk.dto.TeamQuestionsSumDto
import com.chgk.excel.ExcelParser
import com.chgk.excel.StandardXlsxParser
import com.chgk.excel.XlsxParser
import com.chgk.freemarker.TournamentTemplate
import com.chgk.model.Team
import com.chgk.model.Tour
import com.chgk.model.Tournament
import org.apache.logging.log4j.kotlin.Logging

class Main : Logging {

    companion object X : Logging {
        // todo: take from the env variable
        private const val HTML_FILES_PATH = "C:\\java\\chgk-graphs\\docs\\"

        @JvmStatic
        fun main(args: Array<String>) {
            parseOcch2022()
            parseOvsch2022_3()
            parseTriz2022_4()
        }

        private fun parseOvsch2022_3() {
            val tournament = Tournament(
                7700,
                "XX Открытый всеобщий синхронный чемпионат. 3 этап (синхрон)",
                "Дортмунд",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Дидбаридзе"),
                Tour(2, "Разумов"),
                Tour(3, "Шередега")
            )

            val visibleTeamNames = listOf(
                "Эльфы",
                "Сфинкс-party",
                "Дижжон",
                "Авось",
                "Так получилось",
                "Вестфальский Мир"
            )

            generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-7700-07-Nov-2022__2.xlsx",
                "ovsch-2022-3-dortmund.html"
            )
        }

        private fun parseTriz2022_4() {
            val tournament = Tournament(
                8589,
                "I международный синхронный турнир \"TRIZ. 4 этап\"",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Терентьев"),
                Tour(2, "Коробейников"),
                Tour(3, "Мерзляков")
            )

            val visibleTeamNames = listOf(
                "Сфинкс-party",
                "И",
                "Проти вiтру",
                "Ты не один",
                "Ясен Пень",
                "Счастливое число",
                "Авось",
                "Так получилось"
            )

            generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-8589-11-Nov-2022.xlsx",
                "triz-2022-4-duesseldorf.html"
            )
        }

        private fun parseOcch2022() {
            val tournament = Tournament(
                6636,
                "Открытый чемпионат Чехии по «Что? Где? Когда?»",
                "Прага",
                6
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

            val visibleTeamNames = listOf( // not all teams are visible
                "В поисках мема",
                "Котобусер Тор",
                "Игрунки",
                "Авось",
                "Юнона"
            )

            generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                XlsxParser,
                "Результаты Большой игры - 4ОЧЧ.xlsx",
                "ochh-2022.html"
            )
        }

        private fun generateTournamentHtmlToStandardDirectory(
            tournament: Tournament,
            visibleTeamNames: List<String>,
            excelParser: ExcelParser,
            inputExcelFilePath: String,
            htmlFilePath: String
        ) {
            generateTournamentHtml(
                tournament,
                visibleTeamNames,
                excelParser,
                inputExcelFilePath,
                "$HTML_FILES_PATH$htmlFilePath"
            )
        }

        private fun generateTournamentHtml(
            tournament: Tournament,
            visibleTeamNames: List<String>,
            excelParser: ExcelParser,
            inputExcelFilePath: String,
            htmlFilePath: String
        ) {
            excelParser.parseTournament(tournament, inputExcelFilePath)
            logger.info(
                """
                    Tournament \"${tournament.name}\" parsed from file \"$inputExcelFilePath\".
                    Total teams: ${tournament.totalTeams}
                """.trimIndent()
            )

            val teamSums = getTeamSums(tournament)

            val template = TournamentTemplate()
            template.fillTemplateData(tournament, teamSums, visibleTeamNames)
            template.export(htmlFilePath)
            logger.info(
                """
                    Tournament \"${tournament.name}\" to file \"$htmlFilePath\".                 
                """.trimIndent()
            )
        }

        private fun getTeamSums(tournament: Tournament): List<TeamQuestionsSumDto> {
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

        private fun manualTest() { // todo: move this to the unit test or remove
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
