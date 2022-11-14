package com.chgk.excel

import com.chgk.model.Team
import com.chgk.model.TeamTourResults
import com.chgk.model.Tournament
import org.apache.logging.log4j.kotlin.Logging
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.IOException

object XlsxParser : ExcelParser, Logging {

    const val FILE_NAME = "Результаты Большой игры - 4ОЧЧ.xlsx"

    private const val TEAMS_SHEET_NAME = "Команды"
    private const val TEAMS_SHEET_INDEX = 0

    @JvmStatic
    fun main(args: Array<String>) {
        parseWikiPagesDataSafe()
    }

    override fun parseTournament(tournament: Tournament, fileName: String) {
        val teams = parseTeamsSheet(fileName)
        // todo: validate teams (unique names, unique ids, unique numbers)
        tournament.addTeams(teams)

        for (tourNumber in tournament.firstTourNum..tournament.lastTourNum) {
            val tourResults = parseTourSheet(fileName, tourNumber, tournament.questionsPerTour)

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

    fun parseWikiPagesDataSafe() {
        try {
            val fileName = FILE_NAME

            parseTeamsSheet(fileName)
            parseTourSheet(fileName, 1)
            parseTourSheet(fileName, 2)
            parseTourSheet(fileName, 3)
            parseTourSheet(fileName, 4)
            parseTourSheet(fileName, 5)
            parseTourSheet(fileName, 6)
        }
        catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    fun parseTeamsSheet(fileName: String): List<Team> {
        val workbook = parseWorkbook(fileName)

        val sheet = workbook.getSheet(TEAMS_SHEET_NAME)
//        val sheet = workbook.getSheetAt(TEAMS_SHEET_INDEX)

        val teams: MutableList<Team> = ArrayList()

        val firsRowNum = sheet.firstRowNum + 1 // skip header row
        val lastRowNum = sheet.lastRowNum

        for (rowNum in firsRowNum..lastRowNum) {
            val row = sheet.getRow(rowNum)

            val name = getStringSafe(row, 0)
            val city = getStringSafe(row, 1)

//            val id = getStringSafe(row, 2)
            val id = getIntSafe(row, 2)

//            val number = getStringSafe(row, 3)
            val number = getIntSafe(row, 3)

            val team = Team(id!!, name, city, number!!)

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
    fun parseTourSheet(fileName: String, tourNumber: Int, questionsInTour: Int = 12): List<TeamTourResults> {
        val workbook = parseWorkbook(fileName)

        val sheetName = getTourSheetName(tourNumber)
        val sheet = workbook.getSheet(sheetName)

        val firsRowNum = sheet.firstRowNum + 1 // skip header row
        val lastRowNum = sheet.lastRowNum

        val teamsResults: MutableList<TeamTourResults> = ArrayList()

        for (rowNum in firsRowNum..lastRowNum) {
            val row = sheet.getRow(rowNum)

            if (row == null) { // skip empty rows
                logger.info("Row number $rowNum is null. Skipping this row.")
                continue
            }

            var columnNum = 0

            val teamName = getStringSafe(row, columnNum++)
            val teamCity = getStringSafe(row, columnNum++)
            val tourNumberFromSheet = getIntSafe(row, columnNum++) // todo: we may assert this is equal to tourNumber
            val teamNumber = getIntSafe(row, columnNum++)

            if (teamNumber == null || teamNumber == 0) { // skip rows with question rating // ! empty cell is parsed to 0
                logger.info("Row number $rowNum has no team number. Skipping this row.")
                continue
            }

            val teamTourQuestionsAnswered = mutableListOf<Boolean>();

            for (questionNumber in 1..questionsInTour) {
                val questionAnsweredInt = getIntSafe(row, columnNum++)

                // todo: probably check 0 or 1 and fail or set null if no value is set.
                val questionAnswered = (questionAnsweredInt == 1) // todo: 1 to constant

                teamTourQuestionsAnswered.add(questionAnswered)
            }

            // we don't parse "В туре" and "Рейтинг" from the sheet, we will calculate it by ourselves

            // todo: parse to teams results list by tour

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

    private fun getTourSheetName(tourNumber: Int) = "Тур $tourNumber"
}
