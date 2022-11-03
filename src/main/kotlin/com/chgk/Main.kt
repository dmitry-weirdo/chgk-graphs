package com.chgk

import com.chgk.model.Tournament
import org.apache.logging.log4j.kotlin.Logging

class Main : Logging {

    companion object X : Logging {
        @JvmStatic
        fun main(args: Array<String>) {
            val tournament = Tournament(
                6636,
                "Открытый чемпионат Чехии по «Что? Где? Когда?»",
                "Прага"
            )

            logger.info("""
                Tournament id: ${tournament.id} 
                Tournament name: ${tournament.name} 
                Link in old rating: ${tournament.linkInOldRating}
                Link in new rating: ${tournament.linkInNewRating}
                Tours count: ${tournament.toursCount}
                Questions per tour: ${tournament.questionsPerTour}
                Total questions in the tournament: ${tournament.totalQuestions}
            """.trimIndent())
        }
    }
}
