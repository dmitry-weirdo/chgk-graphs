package com.chgk.excel

import com.chgk.model.Team
import com.chgk.model.TeamTourResults
import com.chgk.model.Tournament
import org.apache.logging.log4j.kotlin.Logging
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.IOException

object StandardXlsxParser : Logging {

    private const val TEAMS_SHEET_INDEX = 0

    // headers
    private const val TEAM_ID_COLUMN_INDEX = 0
    private const val TEAM_NAME_COLUMN_INDEX = 1
    private const val TEAM_CITY_COLUMN_INDEX = 2
    private const val TOUR_NUMBER_COLUMN_INDEX = 3

    private const val TEAM_ID_COLUMN_HEADER = "Team ID"
    private const val TEAM_NAME_COLUMN_HEADER = "Название"
    private const val TEAM_CITY_COLUMN_HEADER = "Город"
    private const val TOUR_NUMBER_COLUMN_HEADER = "Тур"

    fun parseTournament(tournament: Tournament, fileName: String) {
        val teams = parseTeamsSheet(fileName)
        // todo: validate teams (unique names, unique ids, unique numbers)
        tournament.addTeams(teams)

        for (tourNumber in tournament.firstTourNum..tournament.lastTourNum) {
            val tourResults = parseTourSheet(fileName, tournament, tourNumber)

            logger.info("Tour $tourNumber: ${tourResults.size} team results parsed.")
            if (tourResults.size != tournament.totalTeams) {
                throw IllegalStateException("Tour $tourNumber: ${tourResults.size} team results parsed, but ${tournament.totalTeams} team results expected.")
            }

            for (teamResult in tourResults) {
                val teamByNumber = tournament.getTeam(teamResult.teamNumber) // will fail if team is not found
                val teamByName = tournament.getTeam(teamResult.teamName) // will fail if team is not found

                if (teamByNumber != teamByName) {
                    throw IllegalStateException("Incorrect team result in tour $tourNumber: teams with number ${teamResult.teamNumber} and name \"%${teamResult.teamName}\" are different teams.")
                }

                // check that team has no result for this tour yet
                val currentTourResult = teamByNumber.getTourResult(tourNumber)
                if (currentTourResult != null) {
                    throw IllegalStateException("Team ${teamByNumber.name} (number ${teamByNumber.tournamentNumber} already has results for tour $tourNumber")
                }

                // add team tour result
                teamByNumber.addTourResult(teamResult)
            }
        }

        // todo: validate tour results (each team must have all results, each team must be present in the teams list)
    }

    @Throws(IOException::class)
    fun parseTeamsSheet(fileName: String): List<Team> {
        val workbook = parseWorkbook(fileName)

        val sheet = workbook.getSheetAt(TEAMS_SHEET_INDEX) // no stable name for the sheet

        val teams: MutableList<Team> = ArrayList()

        val firsRowNum = sheet.firstRowNum // no stable header row
        val lastRowNum = sheet.lastRowNum

        // todo: лучше парсить по заголовкам "Team ID" и "Название"

        var teamNumber = 1

        for (rowNum in firsRowNum..lastRowNum) {
            val row = sheet.getRow(rowNum)

            if (row == null) {
                logger.info("Row $rowNum is null. Skip this row.")
                continue
            }

            if (!isNumeric(row, TEAM_ID_COLUMN_INDEX)) {
                val teamIdColumnValue = getStringSafe(row, TEAM_ID_COLUMN_INDEX)
                if (teamIdColumnValue.isBlank()) {
                    logger.info("Row $rowNum is empty. Skip this row.")
                    continue
                }

                if (teamIdColumnValue.equals(TEAM_ID_COLUMN_HEADER, true)) {
                    logger.info("Row $rowNum is a header row. Skip this row.")
                    continue
                }

                logger.info("Row $rowNum has non-numeric team id value \"$teamIdColumnValue\". Skip this row.")
                continue
            }

            val id = getIntSafe(row, TEAM_ID_COLUMN_INDEX)

            val existingTeamWithSameId = teams.firstOrNull { it.id == id }
            if (existingTeamWithSameId != null) {
                logger.info("Team with id = $id is already present in the teams list. Do not add it again.")
                continue
            }

            val name = getStringSafe(row, TEAM_NAME_COLUMN_INDEX)
            val city = getStringSafe(row, TEAM_CITY_COLUMN_INDEX)

            // we don't have team number here, we generate a pseudo-number automatically
//            val number = getStringSafe(row, 3)
//            val number = getIntSafe(row, 3)

            val team = Team(id!!, name, city, teamNumber++)

            logger.info("""
                Team parsed:
                    name: ${team.name}
                    city: ${team.city}
                    id: ${team.id}
                    number: ${team.tournamentNumber}
            """.trimIndent())

            teams.add(team)
        }

        logger.info("Total teams in the tournament: ${teams.size}")
        return teams
    }

    @Throws(IOException::class)
    private fun parseWorkbook(fileName: String): Workbook {
        // todo: use global file path, not a relative resource file name

        javaClass.classLoader.getResourceAsStream(fileName).use { stream ->  // getResource does not work within jar!
            return XSSFWorkbook(stream)
        }
    }

    @Throws(IOException::class)
    fun parseTourSheet(fileName: String, tournament: Tournament, tourNumber: Int): List<TeamTourResults> {
        val workbook = parseWorkbook(fileName)

        val sheet = workbook.getSheetAt(TEAMS_SHEET_INDEX) // no stable name for the sheet, all results are on the same sheet

        val firsRowNum = sheet.firstRowNum // no stable header row
        val lastRowNum = sheet.lastRowNum

        val teamsResults: MutableList<TeamTourResults> = ArrayList()

        for (rowNum in firsRowNum..lastRowNum) {
            val row = sheet.getRow(rowNum)

            if (row == null) {
                logger.info("Row $rowNum is null. Skip this row.")
                continue
            }

            if (!isNumeric(row, TEAM_ID_COLUMN_INDEX)) {
                val teamIdColumnValue = getStringSafe(row, TEAM_ID_COLUMN_INDEX)
                if (teamIdColumnValue.isBlank()) {
                    logger.info("Row $rowNum is empty. Skip this row.")
                    continue
                }

                if (teamIdColumnValue.equals(TEAM_ID_COLUMN_HEADER, true)) {
                    logger.info("Row $rowNum is a header row. Skip this row.")
                    continue
                }

                logger.info("Row $rowNum has non-numeric team id value \"$teamIdColumnValue\". Skip this row.")
                continue
            }

            // should be the team row -> parse it
            var columnNum = 0

            val teamId = getIntSafe(row, columnNum++)
            val teamName = getStringSafe(row, columnNum++)
            val teamCity = getStringSafe(row, columnNum++)

            val tourNumberFromSheet = getIntSafe(row, columnNum++) // todo: we may assert this is equal to tourNumber
            if (tourNumberFromSheet != tourNumber) {
                logger.info("Row $rowNum has tourNumber = $tourNumberFromSheet, expected tourNumber = $tourNumber. Skip this row.")
                continue
            }

            val teamNumber = tournament.getTeamById(teamId!!).tournamentNumber

            val teamTourQuestionsAnswered = mutableListOf<Boolean>();

            for (questionNumber in 1 .. tournament.questionsPerTour) {
                if (!isNumeric(row, columnNum)) {
                    // todo: спорные тоже нужно собирать в результаты, как строки

                    val controversialAnswer = getStringSafe(row, columnNum)

                    // спорный пока засчитывается как неверный ответ
                    logger.info("Tour $tourNumber, team $teamId (\"$teamName\"): answer \"$controversialAnswer\" is controversial. Its current result will be \"not answered\".")

                    val questionAnswered = false

                    teamTourQuestionsAnswered.add(questionAnswered)
                }
                else {
                    // обычный ответ - + или -
                    val questionAnsweredInt = getIntSafe(row, columnNum)

                    // todo: probably check 0 or 1 and fail or set null if no value is set.
                    val questionAnswered = (questionAnsweredInt == 1) // todo: 1 to constant

                    teamTourQuestionsAnswered.add(questionAnswered)
                }

                // in any case, proceed to the next column
                columnNum++
            }

            // we don't parse "В туре" and "Рейтинг" from the sheet, we will calculate it by ourselves

            logger.info("""
                Row: $rowNum: Team results in tour ${tourNumber} parsed:
                    name: ${teamName} 
                    city: ${teamCity} 
                    number: ${teamNumber} 
                    answers: ${teamTourQuestionsAnswered} 
            """.trimIndent())

            val teamTourResults = TeamTourResults(tourNumber, teamName, teamNumber, teamTourQuestionsAnswered)
            teamsResults.add(teamTourResults)
        }

        return teamsResults
    }

    private fun isNumeric(row: Row, cellNumber: Int): Boolean {
        val cell = row.getCell(cellNumber) ?: return false

        return (cell.cellType == CellType.NUMERIC)
    }

    private fun getStringSafe(row: Row, cellNumber: Int): String {
        val cell = row.getCell(cellNumber)
            ?: return ""

        return cell.stringCellValue
    }

    private fun getIntSafe(row: Row, cellNumber: Int): Int? {
        val cell = row.getCell(cellNumber)
            ?: return null

        return cell.numericCellValue.toInt()
    }
}
