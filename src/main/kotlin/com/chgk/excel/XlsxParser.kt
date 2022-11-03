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
            parseWikiPagesData()
        }
        catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    fun parseWikiPagesData() {
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

            val team = Team(id!!, name, city) // todo: also pass team number in the tournament

            // todo: parse to teams list

            logger.info("""
                Team parsed:
                    name: ${name} 
                    city: ${city} 
                    id: ${id} 
                    number: ${number} 
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
