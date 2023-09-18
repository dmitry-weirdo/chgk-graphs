package com.chgk.config

import com.chgk.excel.ExcelParser
import com.chgk.excel.StandardXlsxParser
import com.chgk.excel.XlsxParser

data class GeneratorConfig(
    val visibleTeamNames: List<String>,
    val inputExcelFilePath: String,
    val htmlFileName: String,
    val excelParser: String = "StandardXlsxParser"
) {
    val parser: ExcelParser
        get() = when (excelParser) {
            "StandardXlsxParser" -> StandardXlsxParser
            "XlsxParser" -> XlsxParser
            else -> error("Unknown excelParser: $excelParser")
        }
}
