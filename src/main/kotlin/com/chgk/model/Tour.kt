package com.chgk.model

/**
 * Тур в пакете.
 *
 * @property number номер тура, начиная с 1.
 * @property editor редактор тура, в произвольной форме. Рекомендуется указывать имя и фамилию редактора.
 * @property questionsCount число вопросов в туре.
 */
data class Tour (
    val number: Int,
    val editor: String,
    val questionsCount: Int = 12,
    val from: Int = (number - 1) * questionsCount + 1, // 1, 13, 25, ...
    val to: Int = (number) * questionsCount // 12, 24, 36, ...

    // todo: add List<Question> if required
)
