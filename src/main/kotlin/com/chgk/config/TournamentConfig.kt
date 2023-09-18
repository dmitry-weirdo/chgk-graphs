package com.chgk.config

import com.chgk.model.Tournament

data class TournamentConfig(
    val tournament: Tournament,
    val generatorConfig: GeneratorConfig
)
