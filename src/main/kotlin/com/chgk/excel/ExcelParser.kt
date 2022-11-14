package com.chgk.excel

import com.chgk.model.Tournament
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row

interface ExcelParser {
    fun parseTournament(tournament: Tournament, fileName: String)

    fun isNumeric(row: Row, cellNumber: Int): Boolean {
        val cell = row.getCell(cellNumber) ?: return false

        return (cell.cellType == CellType.NUMERIC)
    }

    fun getStringSafe(row: Row, cellNumber: Int): String {
        val cell = row.getCell(cellNumber)
            ?: return ""

        return cell.stringCellValue
    }

    fun getIntSafe(row: Row, cellNumber: Int): Int? {
        val cell = row.getCell(cellNumber)
            ?: return null

        return cell.numericCellValue.toInt()
    }
}
