package com.chgk.freemarker

import com.chgk.TournamentGenerator

class IndexTemplate : FreemarkerTemplate() {

    override val templatePath: String
        get() = "/ftl/index.ftl"

    fun fillTemplateData(generators: List<TournamentGenerator>) {
        templateData["generators"] = generators
    }
}
