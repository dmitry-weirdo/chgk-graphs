package com.chgk

import com.chgk.config.TournamentConfig
import com.chgk.excel.ExcelParser
import com.chgk.excel.StandardXlsxParser
import com.chgk.excel.XlsxParser
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

            var index = 0

            val generators = listOf(
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                generateTournamentHtmlToStandardDirectory(configs[index++]),
                parse_ostrovok_besk(),
                parse_besk_zemli_35(),
                parse_slavjanka_bez_razdatok_5_stage_1_2023(),
                parse_SPV_winter_2023(),
                parseSimpleDecoration_2023(),
                parseEasySmolensk5_2023(),
                parseSecondRaven(),
                parseOlivierCup_2023(),
                parseNevMoGoTu_2022(),
                parseEasySmolensk20(),
                parseEkvestria4(),
                parseTop1000_2022(),
                parseTriz2022_4(),
                parseOvsch2022_3(),
                parseOcch2022(),
            )

            val template = IndexTemplate()
            template.fillTemplateData(generators)
            template.export(INDEX_FILE_PATH)
            logger.info("${generators.size} tournaments list generated to the index file \"$INDEX_FILE_PATH\".")
        }

        private fun parse_ostrovok_besk(): TournamentGenerator {
            val tournament = Tournament(
                8809,
                "Островок Бесконечности",
                "Дюссельдорф"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Мухаметкалиев"),
                Tour(2, "Мерзляков"),
                Tour(3, "Саксонов")
            )

            val visibleTeamNames = listOf(
                "Счастливое число",
                "Так получилось",
                "Сфинкс-party",
                "ЖмеR",
                "И",
                "Авось",
                "Ясен пень"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8809-15-Feb-2023.xlsx",
                "ostrovok-besk.html"
            )
        }

        private fun parse_besk_zemli_35(): TournamentGenerator {
            val tournament = Tournament(
                8793,
                "Бесконечные Земли. Том XXXV (синхрон)",
                "Дюссельдорф"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Полевой / Рыбачук"),
                Tour(2, "Ермишкин"),
                Tour(3, "Саксонов")
            )

            val visibleTeamNames = listOf(
                "И",
                "Жмеринка",
                "Авось",
                "ИК Ковчег"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8793-04-Feb-2023-wc.xlsx",
                "besk-zemli-35.html"
            )
        }

        private fun parse_slavjanka_bez_razdatok_5_stage_1_2023(): TournamentGenerator {
            val tournament = Tournament(
                8871,
                "V международный синхронный турнир \"Славянка без раздаток. 1 этап\"",
                "Дортмунд",
                questionsPerTour = 15
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Мерзляков", questionsCount = 15),
                Tour(2, "Терентьев", questionsCount = 15),
                Tour(3, "Колмаков", questionsCount = 15)
            )

            val visibleTeamNames = listOf(
                "Легион",
                "Раздолье",
                "Авось",
                "Эльф"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8871-30-Jan-2023.xlsx",
                "sbr-5-stage-1-2023.html"
            )
        }

        private fun parse_SPV_winter_2023(): TournamentGenerator {
            val tournament = Tournament(
                8734,
                "Синхрон простых вопросов. Зима – 2023",
                "Дортмунд"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Шорин"),
                Tour(2, "Волков"),
                Tour(3, "Мерзляков")
            )

            val visibleTeamNames = listOf(
                "Легион",
                "Раздолье",
                "Авось",
                "Эльфы"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8734-30-Jan-2023_ре.xlsx",
                "spv-winter-2023-dortmund.html"
            )
        }

        private fun parseSimpleDecoration_2023(): TournamentGenerator {
            val tournament = Tournament(
                8802,
                "Международный синхронный турнир \"Простое украшенье\" 2023",
                "Дортмунд"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Раздолье",
                "Авось",
                "Эльфы-Лайт"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8802-25-Jan-2023.xlsx",
                "simple-decoration-2023-dortmund.html"
            )
        }

        private fun parseEasySmolensk5_2023(): TournamentGenerator {
            val tournament = Tournament(
                8803,
                "Международный синхронный турнир \"Простой Смоленск - 5\"",
                "Дортмунд",
                questionsPerTour = 13
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1", 13),
                Tour(2, "Тур 2", 13),
                Tour(3, "Тур 3", 13)
            )

            val visibleTeamNames = listOf(
                "Раздолье",
                "Авось",
                "Эльфы"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8803-25-Jan-2023__1.xlsx",
                "easy-smolensk-5-2023-dortmund.html"
            )
        }

        private fun parseSecondRaven(): TournamentGenerator {
            val tournament = Tournament(
                8835,
                "Второй Ворон - синхрон",
                "Дюссельдорф"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Алиев"),
                Tour(2, "Шибанов"),
                Tour(3, "Саксонов")
            )

            val visibleTeamNames = listOf(
                "Так получилось",
                "И",
                "Ясен Пень",
                "Трое в лодке",
                "Авось",
                "ЖмеR",
                "Сфинкс-party"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8835-20-Jan-2023.xlsx",
                "second-raven-2023-duesseldorf.html"
            )
        }

        private fun parseOlivierCup_2023(): TournamentGenerator {
            val tournament = Tournament(
                8755,
                "Международный синхронный турнир \"Кубок Оливье 2023\"",
                "Дюссельдорф"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Так получилось",
                "ЖмеR",
                "Ясен Пень",
                "И",
                "Счастливое число",
                "Два капитана",
                "Авось",
                "Сфинкс-party",
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8755-07-Jan-2023.xlsx",
                "olivier-cup-2023-duesseldorf.html"
            )
        }

        private fun parseNevMoGoTu_2022(): TournamentGenerator {
            val tournament = Tournament(
                8791,
                "Международный синхронный чемпионат \"Кубок невМоГоТУ - 2022\"",
                "Дортмунд"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "И",
                "Раздолье",
                "Сборная NRW",
                "Авось",
                "Эльфы",
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8791-29-Dec-2022.xlsx",
                "nevmogotu-2022-dortmund.html"
            )
        }

        private fun parseEasySmolensk20(): TournamentGenerator {
            val tournament = Tournament(
                8669,
                "Международный синхронный турнир \"Лёгкий Смоленск - 20\"",
                "Дюссельдорф",
                questionsPerTour = 13
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1", 13),
                Tour(2, "Тур 2", 13),
                Tour(3, "Тур 3", 13)
            )

            val visibleTeamNames = listOf(
                "И",
                "Так получилось",
                "ЖмеR",
                "Сфинкс-party",
                "Ясен Пень",
                "ИК Ковчег",
                "Авось",
                "Счастливое число",
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8669-28-Dec-2022.xlsx",
                "easy-smolensk-20-duesseldorf.html"
            )
        }

        private fun parseEkvestria4(): TournamentGenerator {
            val tournament = Tournament(
                8633,
                "Кубок Эквестрии – 4: Cold Storm (синхрон)",
                "Дюссельдорф"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Коробейников"),
                Tour(2, "Алиев"),
                Tour(3, "Казначеев")
            )

            val visibleTeamNames = listOf(
                "И",
                "Так получилось",
                "Сфинкс-party",
                "Ясен Пень",
                "ИК Ковчег",
                "Авось",
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8633-13-Dec-2022.xlsx",
                "ekvestria-4-duesseldorf.html"
            )
        }

        private fun parseTop1000_2022(): TournamentGenerator {
            val tournament = Tournament(
                8561,
                "Топ-1000 XIII (синхрон)",
                "Дюссельдорф"
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Так получилось",
                "Сфинкс-party",
                "Ясен Пень",
                "ЖмеR",
                "Авось",
                "Один в поле"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8561-24-Nov-2022_н.xlsx",
                "top-1000-2022-3-duesseldorf.html"
            )
        }

        private fun parseOvsch2022_3(): TournamentGenerator {
            val tournament = Tournament(
                7700,
                "XX Открытый всеобщий синхронный чемпионат. 3 этап (синхрон)",
                "Дортмунд"
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

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-7700-07-Nov-2022__2.xlsx",
                "ovsch-2022-3-dortmund.html"
            )
        }

        private fun parseTriz2022_4(): TournamentGenerator {
            val tournament = Tournament(
                8589,
                "I международный синхронный турнир \"TRIZ. 4 этап\"",
                "Дюссельдорф"
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

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "tournament-tours-8589-11-Nov-2022.xlsx",
                "triz-2022-4-duesseldorf.html"
            )
        }

        private fun parseOcch2022(): TournamentGenerator {
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

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                "Результаты Большой игры - 4ОЧЧ.xlsx",
                "ochh-2022.html",
                XlsxParser
            )
        }

        private fun generateTournamentHtmlToStandardDirectory(tournamentConfig: TournamentConfig) =
            generateTournamentHtmlToStandardDirectory(
                tournamentConfig.tournament,
                tournamentConfig.generatorConfig.visibleTeamNames,
                tournamentConfig.generatorConfig.inputExcelFilePath,
                tournamentConfig.generatorConfig.htmlFileName,
                tournamentConfig.generatorConfig.excelParser
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
