package com.chgk

import com.chgk.excel.ExcelParser
import com.chgk.excel.StandardXlsxParser
import com.chgk.excel.XlsxParser
import com.chgk.freemarker.IndexTemplate
import com.chgk.model.Team
import com.chgk.model.Tour
import com.chgk.model.Tournament
import org.apache.logging.log4j.kotlin.Logging

class Main : Logging {

    companion object X : Logging {
        // todo: take from the env variable
        private const val HTML_FILES_DIRECTORY = "C:\\java\\chgk-graphs\\docs\\"
        private const val INDEX_FILE_NAME = "index.html"
        private const val INDEX_FILE_PATH = "$HTML_FILES_DIRECTORY$INDEX_FILE_NAME"

        @JvmStatic
        fun main(args: Array<String>) {
            val generators = listOf(
                parse_ultima_tule_2023(),
                parse_premier_sunny_days_2023(),
                parseEkvestria9(),
                parse_hypercube_3(),
                parse_ostrovok_besk_may_2023(),
                parse_SPV_spring_2023(),
                parseEkvestria8(),
                parse_lm_orange_light(),
                parse_premier_cup_of_light_horses_2023(),
                parse_cube_8(),
                parse_12_graney_1(),
                parseTriz_2023_1(),
                parsePremierMarchYdes(),
                parseEkvestria7(),
                parseEkvestria6(),
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

        private fun parse_ultima_tule_2023(): TournamentGenerator {
            val tournament = Tournament(
                9487,
                "Зеркало Ultima Tule",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Шибанов"),
                Tour(2, "Мерзляков"),
                Tour(3, "Коробейников")
            )

            val visibleTeamNames = listOf(
                "Ясень Пень",
                "ЖмеR",
                "Проти вiтру",
                "Авось",
                "И",
                "Сфинкс-party"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament_tours_9487_16_Jul_2023_Ултима_Туле_1_Испр.xlsx",
                "ultima-tule-2023-duesseldorf.html"
            )
        }


        private fun parse_premier_sunny_days_2023(): TournamentGenerator {
            val tournament = Tournament(
                9398,
                "Серия Premier. Солнечные дни – 2023",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Ясень Пень",
                "И",
                "Раздолье",
                "Берлина кого-то привела",
                "Рациональное число",
                "Счастливое число",
                "Авось",
                "ЖмеR"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament_tours_9398_30_Jun_2023_1_Серия_Premier_измен.xlsx",
                "premier-sunny-days-2023-duesseldorf.html"
            )
        }


        private fun parseEkvestria9(): TournamentGenerator {
            val tournament = Tournament(
                9196,
                "Кубок Эквестрии — 9: Raise the Sun",
                "Дортмунд",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Алиев"),
                Tour(2, "Мерзляков"),
                Tour(3, "Колесов")
            )

            val visibleTeamNames = listOf(
                "Грудка Ктулху",
                "Авидас расслабонис",
                "Эйфория"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-9196-31-May-2023.xlsx",
                "ekvestria-9-dortmund.html"
            )
        }

        private fun parse_hypercube_3(): TournamentGenerator {
            val tournament = Tournament(
                8917,
                "III сезон синхронного турнира «Куб». Финал (Гиперкуб)",
                "Дюссельдорф",
                3,
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Брюханов", 6),
                Tour(2, "Кунилов / Парр", 6),
                Tour(3, "Пономарёв / Столяров", 6),
                Tour(4, "Иванов / Сахаров", 6),
                Tour(5, "Хаиткулов / Сахаров", 6),
                Tour(6, "Коврижных", 6),
            )

            val visibleTeamNames = listOf(
                "Ясен Пень",
                "Счастливое число",
                "Проти вiтру",
                "Авось",
                "И",
                "ЖмеR",
                "Сфинкс-party",
                "Сборная качалки"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-8917-28-May-2023.xlsx",
                "hypercube-3.html",
            )
        }

        private fun parse_ostrovok_besk_may_2023(): TournamentGenerator {
            val tournament = Tournament(
                9070,
                "Островок Бесконечности: май",
                "Дортмунд",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Гулецкий"),
                Tour(2, "Казначеев"),
                Tour(3, "Саксонов")
            )

            val visibleTeamNames = listOf(
                "Грудка Ктулху",
                "Авось",
                "Эльфы",
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-9070-22-May-2023.xlsx",
                "ostrovok-besk-may-2023.html"
            )
        }

        private fun parse_SPV_spring_2023(): TournamentGenerator {
            val tournament = Tournament(
                9018,
                "Синхрон простых вопросов. Весна – 2023",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Шорин"),
                Tour(2, "Богатов"),
                Tour(3, "Мерзляков")
            )

            val visibleTeamNames = listOf(
                "Ясен Пень",
                "И",
                "Авось",
                "Бездашенные",
                "Сфинкс-party",
                "Счастливое число",
                "ЖмеR"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-9018-13-May-2023.xlsx",
                "spv-spring-2023-duesseldorf.html"
            )
        }


        private fun parseEkvestria8(): TournamentGenerator {
            val tournament = Tournament(
                9033,
                "Кубок Эквестрии – 8: Sprinkle Medley",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Алиев"),
                Tour(2, "Евтушков"),
                Tour(3, "Полевой / Рыбачук")
            )

            val visibleTeamNames = listOf(
                "Ясен Пень",
                "Проти вiтру",
                "Счастливое число",
                "Авось",
                "И",
                "Сфинкс-party",
                "ЖмеR",
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-9033-01-May-2023.xlsx",
                "ekvestria-8-duesseldorf.html"
            )
        }


        private fun parse_lm_orange_light(): TournamentGenerator {
            val tournament = Tournament(
                9116,
                "LM Оранжевый. Лёгкий",
                "Дортмунд",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Сфинкс-party",
                "Раздолье",
                "Авось",
                "Эльфы"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-9116-25-Apr-2023.xlsx",
                "lm-orange-light-dortmund.html"
            )
        }

        private fun parse_premier_cup_of_light_horses_2023(): TournamentGenerator {
            val tournament = Tournament(
                9014,
                "Серия Premier. Кубок светлых лошадок – 2023",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Пол Пня",
                "ЖмеR",
                "Друзи",
                "Сборная легионеров"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-9014-16-Apr-2023.xlsx",
                "premier-cup-of-light-horses-2023-duesseldorf.html"
            )
        }

        private fun parse_cube_8(): TournamentGenerator {
            val tournament = Tournament(
                8425,
                "III сезон синхронного турнира «Куб». Этап 8. Апрель",
                "Дортмунд",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Раздолье",
                "Сфинкс-party",
                "Эльфы",
                "Филин Dank"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-8425-06-Apr-2023__1.xlsx",
                "cube-8-dortmund.html"
            )
        }

        private fun parse_12_graney_1(): TournamentGenerator {
            val tournament = Tournament(
                8842,
                "Синхронный турнир \"12 граней-2023. Первая игра.\"",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Тур 1"),
                Tour(2, "Тур 2"),
                Tour(3, "Тур 3")
            )

            val visibleTeamNames = listOf(
                "Счастливое число",
                "Авось",
                "Сфинкс-party",
                "И",
                "Так получилось",
                "Ясен Пень",
                "Проти вiтру"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-8842-01-Apr-2023_1.xlsx",
                "12-graney-1-duesseldorf.html"
            )
        }

        private fun parseTriz_2023_1(): TournamentGenerator {
            val tournament = Tournament(
                9013,
                "II международный синхронный турнир \"TRIZ. 1 этап\"",
                "Дортмунд",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Колмаков"),
                Tour(2, "Ершов"),
                Tour(3, "Солахян")
            )

            val visibleTeamNames = listOf(
                "Филин Dank",
                "А если найдем?",
                "ЖмеR",
                "Раздолье",
                "Авось",
                "Эльфы"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-9013-27-Mar-2023__2.xlsx",
                "triz-2023-1-dortmund.html"
            )
        }

        private fun parsePremierMarchYdes(): TournamentGenerator {
            val tournament = Tournament(
                8740,
                "Серия Premier. Мартовские иды – 2023",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Коробейников"),
                Tour(2, "Мерзляков"),
                Tour(3, "Шорин")
            )

            val visibleTeamNames = listOf(
                "И",
                "ЖмеR",
                "Так получилось",
                "Счастливое число",
                "Сфинкс-party"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-8740-16-Mar-2023.xlsx",
                "premier-march-ydes-duesseldorf.html"
            )
        }


        private fun parseEkvestria7(): TournamentGenerator {
            val tournament = Tournament(
                8951,
                "Кубок Эквестрии – 7: Winter Wrap Up!",
                "Дортмунд",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Алиев"),
                Tour(2, "Файнбурд"),
                Tour(3, "Михеев")
            )

            val visibleTeamNames = listOf(
                "Раздолье",
                "Так получилось",
                "Авось",
                "Эльфы"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-8951-13-Mar-2023.xlsx",
                "ekvestria-7-dortmund.html"
            )
        }

        private fun parseEkvestria6(): TournamentGenerator {
            val tournament = Tournament(
                8873,
                "Кубок Эквестрии – 6: Blizzard Whirl",
                "Дюссельдорф",
                3
            )

            // tours metadata are not parsed from Excel
            tournament.addTours(
                Tour(1, "Карясов"),
                Tour(2, "Зырянов"),
                Tour(3, "Алиев")
            )

            val visibleTeamNames = listOf(
                "Сфинкс-party",
                "ЖмеR",
                "Счастливое число",
                "И",
                "Так получилось",
                "Проти вiтра",
                "Авось"
            )

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-8873-03-Mar-2023.xlsx",
                "ekvestria-6-duesseldorf.html"
            )
        }

        private fun parse_ostrovok_besk(): TournamentGenerator {
            val tournament = Tournament(
                8809,
                "Островок Бесконечности",
                "Дюссельдорф",
                3
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
                StandardXlsxParser,
                "tournament-tours-8809-15-Feb-2023.xlsx",
                "ostrovok-besk.html"
            )
        }

        private fun parse_besk_zemli_35(): TournamentGenerator {
            val tournament = Tournament(
                8793,
                "Бесконечные Земли. Том XXXV (синхрон)",
                "Дюссельдорф",
                3
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
                StandardXlsxParser,
                "tournament-tours-8793-04-Feb-2023-wc.xlsx",
                "besk-zemli-35.html"
            )
        }

        private fun parse_slavjanka_bez_razdatok_5_stage_1_2023(): TournamentGenerator {
            val tournament = Tournament(
                8871,
                "V международный синхронный турнир \"Славянка без раздаток. 1 этап\"",
                "Дортмунд",
                3,
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
                StandardXlsxParser,
                "tournament-tours-8871-30-Jan-2023.xlsx",
                "sbr-5-stage-1-2023.html"
            )
        }

        private fun parse_SPV_winter_2023(): TournamentGenerator {
            val tournament = Tournament(
                8734,
                "Синхрон простых вопросов. Зима – 2023",
                "Дортмунд",
                3
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
                StandardXlsxParser,
                "tournament-tours-8734-30-Jan-2023_ре.xlsx",
                "spv-winter-2023-dortmund.html"
            )
        }

        private fun parseSimpleDecoration_2023(): TournamentGenerator {
            val tournament = Tournament(
                8802,
                "Международный синхронный турнир \"Простое украшенье\" 2023",
                "Дортмунд",
                3
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
                StandardXlsxParser,
                "tournament-tours-8802-25-Jan-2023.xlsx",
                "simple-decoration-2023-dortmund.html"
            )
        }

        private fun parseEasySmolensk5_2023(): TournamentGenerator {
            val tournament = Tournament(
                8803,
                "Международный синхронный турнир \"Простой Смоленск - 5\"",
                "Дортмунд",
                3,
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
                StandardXlsxParser,
                "tournament-tours-8803-25-Jan-2023__1.xlsx",
                "easy-smolensk-5-2023-dortmund.html"
            )
        }

        private fun parseSecondRaven(): TournamentGenerator {
            val tournament = Tournament(
                8835,
                "Второй Ворон - синхрон",
                "Дюссельдорф",
                3
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
                StandardXlsxParser,
                "tournament-tours-8835-20-Jan-2023.xlsx",
                "second-raven-2023-duesseldorf.html"
            )
        }

        private fun parseOlivierCup_2023(): TournamentGenerator {
            val tournament = Tournament(
                8755,
                "Международный синхронный турнир \"Кубок Оливье 2023\"",
                "Дюссельдорф",
                3
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
                StandardXlsxParser,
                "tournament-tours-8755-07-Jan-2023.xlsx",
                "olivier-cup-2023-duesseldorf.html"
            )
        }

        private fun parseNevMoGoTu_2022(): TournamentGenerator {
            val tournament = Tournament(
                8791,
                "Международный синхронный чемпионат \"Кубок невМоГоТУ - 2022\"",
                "Дортмунд",
                3
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
                StandardXlsxParser,
                "tournament-tours-8791-29-Dec-2022.xlsx",
                "nevmogotu-2022-dortmund.html"
            )
        }

        private fun parseEasySmolensk20(): TournamentGenerator {
            val tournament = Tournament(
                8669,
                "Международный синхронный турнир \"Лёгкий Смоленск - 20\"",
                "Дюссельдорф",
                3,
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
                StandardXlsxParser,
                "tournament-tours-8669-28-Dec-2022.xlsx",
                "easy-smolensk-20-duesseldorf.html"
            )
        }

        private fun parseEkvestria4(): TournamentGenerator {
            val tournament = Tournament(
                8633,
                "Кубок Эквестрии – 4: Cold Storm (синхрон)",
                "Дюссельдорф",
                3
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
                StandardXlsxParser,
                "tournament-tours-8633-13-Dec-2022.xlsx",
                "ekvestria-4-duesseldorf.html"
            )
        }

        private fun parseTop1000_2022(): TournamentGenerator {
            val tournament = Tournament(
                8561,
                "Топ-1000 XIII (синхрон)",
                "Дюссельдорф",
                3
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
                StandardXlsxParser,
                "tournament-tours-8561-24-Nov-2022_н.xlsx",
                "top-1000-2022-3-duesseldorf.html"
            )
        }

        private fun parseOvsch2022_3(): TournamentGenerator {
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

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
                "tournament-tours-7700-07-Nov-2022__2.xlsx",
                "ovsch-2022-3-dortmund.html"
            )
        }

        private fun parseTriz2022_4(): TournamentGenerator {
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

            return generateTournamentHtmlToStandardDirectory(
                tournament,
                visibleTeamNames,
                StandardXlsxParser,
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
            htmlFileName: String
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
