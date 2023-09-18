package com.chgk

import com.chgk.config.TournamentConfig
import com.chgk.excel.ExcelParser
import com.chgk.excel.StandardXlsxParser
import com.chgk.freemarker.IndexTemplate
import com.chgk.model.Team
import com.chgk.model.Tour
import com.chgk.model.Tournament
import com.chgk.util.JacksonUtils
import org.apache.logging.log4j.kotlin.Logging

class Main : Logging {

    companion object X : Logging {
        // todo: take from the env variable
        private const val HTML_FILES_DIRECTORY = "C:\\java\\chgk-graphs\\docs\\"
        private const val INDEX_FILE_NAME = "index.html"
        private const val INDEX_FILE_PATH = "$HTML_FILES_DIRECTORY$INDEX_FILE_NAME"
        private const val CONFIGS_FILE_PATH = "C:\\java\\chgk-graphs\\src\\main\\resources\\json\\tournaments.json"

        @JvmStatic
        fun main(args: Array<String>) {
            val configsList = JacksonUtils.parseConfigs(CONFIGS_FILE_PATH)
            val configs = configsList.configs

            val generators = configs.map { generateTournamentHtmlToStandardDirectory(it) }

            val template = IndexTemplate()
            template.fillTemplateData(generators)
            template.export(INDEX_FILE_PATH)
            logger.info("${generators.size} tournaments list generated to the index file \"$INDEX_FILE_PATH\".")
        }

        private fun generateTournamentHtmlToStandardDirectory(tournamentConfig: TournamentConfig) =
            generateTournamentHtmlToStandardDirectory(
                tournamentConfig.tournament,
                tournamentConfig.generatorConfig.visibleTeamNames,
                tournamentConfig.generatorConfig.inputExcelFilePath,
                tournamentConfig.generatorConfig.htmlFileName,
                tournamentConfig.generatorConfig.parser
            )

        private fun generateTournamentHtmlToStandardDirectory(
            tournament: Tournament,
            visibleTeamNames: List<String>,
            inputExcelFilePath: String,
            htmlFileName: String,
            excelParser: ExcelParser = StandardXlsxParser
        ): TournamentGenerator {
            val generator = TournamentGenerator(
                tournament,
                visibleTeamNames,
                excelParser,
                inputExcelFilePath,
                HTML_FILES_DIRECTORY,
                htmlFileName
            )

            generator.generateHtml()

            return generator
        }

        private fun manualTest() { // todo: move this to the unit test or remove
            val tournament = Tournament(
                6636,
                "Открытый чемпионат Чехии по «Что? Где? Когда?»",
                "Прага",
                6
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
