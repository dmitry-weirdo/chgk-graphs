package com.chgk.excel

import com.chgk.model.Team
import org.apache.logging.log4j.kotlin.Logging
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.IOException

object XlsxParser : Logging {

    private const val FILE_NAME = "Результаты Большой игры - 4ОЧЧ.xlsx"

    private const val TEAMS_SHEET_NAME = "Команды"
    private const val TEAMS_SHEET_INDEX = 0

    @JvmStatic
    fun main(args: Array<String>) {
        parseWikiPagesDataSafe()
    }

//    fun parseWikiPagesDataSafe(): List<WikiPageData> {
    fun parseWikiPagesDataSafe() {
        try {
            parseTeamsSheet()
            parseTourSheet(1)
            parseTourSheet(2)
            parseTourSheet(3)
            parseTourSheet(4)
            parseTourSheet(5)
            parseTourSheet(6)
        }
        catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    fun parseTeamsSheet() {
        val workbook = parseWorkbook(FILE_NAME)

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
//        return teams
    }

    @Throws(IOException::class)
    private fun parseWorkbook(fileName: String): Workbook {
        javaClass.classLoader.getResourceAsStream(fileName).use { stream ->  // getResource does not work within jar!
            return XSSFWorkbook(stream)
        }
    }

    @Throws(IOException::class)
    fun parseTourSheet(tourNumber: Int, questionsInTour: Int = 12) {
        val workbook = parseWorkbook(FILE_NAME)

        val sheetName = getTourSheetName(tourNumber)
        val sheet = workbook.getSheet(sheetName)

        val firsRowNum = sheet.firstRowNum + 1 // skip header row
        val lastRowNum = sheet.lastRowNum

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

            for (questionNumber in 1 .. questionsInTour) {
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

            // todo: add to results list
        }

    }

    private fun getTourSheetName(tourNumber: Int) = "Тур $tourNumber"

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
