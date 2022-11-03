package com.chgk.model

/**
 * Вопрос ЧГК.
 *
 * @property tournamentNumber номер в турнире (сквозной по всем турам). Обычно начинается с 1.
 * @property tourNumber номер в тур
 * @property alias краткое описание вопроса, чтобы было понятно, о чём он. Это НЕпубличная информация до опубликования турнира.
 * @property rating рейтинг вопроса в турнире. Равен 1 + (количество команд, НЕ&nbsp;взявших вопрос)
 */
data class Question(
    val tournamentNumber: Int,
    val tourNumber: Int,
    val alias: String? = null,
    var rating: Int? = null
    // todo: можно добавить автора, источник, текст итд
) {
}
