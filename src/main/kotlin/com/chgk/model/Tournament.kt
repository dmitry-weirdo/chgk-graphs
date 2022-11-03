package com.chgk.model

/**
 * Турнир по ЧГК.
 *
 * @property id идентификатор турнира на сайтах рейтинга
 * @property name название турнира
 * @property city город/населённый пункт, в котором происходит отыгрыш турнира
 * @property toursCount количество туров
 * @property questionsPerTour количество вопросов в туре
 */
data class Tournament (
    val id: Int,
    val name: String,
    val city: String, // todo: probably make city a separate class /  enum
    // todo: add type (Турнир, синхрон, асинхрон, usw - check the rating site API)
    var toursCount: Int = 6,
    var questionsPerTour: Int = 12
) {
    val linkInOldRating: String
        get() = "https://rating.chgk.info/tournament/${id}"

    val linkInNewRating: String
        get() = "https://rating.maii.li/b/tournament/${id}/"

    val totalQuestions: Int
        get() = toursCount * questionsPerTour

    // todo: teams
    // todo: get total teams
}
