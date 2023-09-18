package com.chgk.config

import com.chgk.excel.ExcelParser
import com.chgk.excel.StandardXlsxParser

data class GeneratorConfig(
    val visibleTeamNames: List<String>,
    val inputExcelFilePath: String,
    val htmlFileName: String,
    val excelParser: ExcelParser = StandardXlsxParser
)
