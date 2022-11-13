package com.chgk.freemarker

import freemarker.template.Configuration
import freemarker.template.TemplateException
import freemarker.template.Version
import lombok.extern.log4j.Log4j2
import java.io.FileWriter
import java.io.IOException
import java.io.StringWriter
import java.io.Writer
import java.nio.charset.StandardCharsets

@Log4j2
abstract class FreemarkerTemplate {
    protected var templateData: MutableMap<String, Any> = HashMap()

    /**
     * @return file path in `/resources`
     */
    abstract val templatePath: String

    fun export(filePath: String) {
        exportFreemarkerToFile(templatePath, filePath, templateData)
    }

    fun exportToString(): String {
        return exportFreemarkerToString(templatePath, templateData)
    }

    companion object {
        private const val FREEMARKER_VERSION = "2.3.31"

        protected fun exportFreemarkerToFile(
            ftlTemplate: String,
            filePath: String,
            templateData: Map<String, Any>
        ) {
            try {
                FileWriter(filePath).use { out -> export(ftlTemplate, templateData, out) }
            }
            catch (e: IOException) {
                throw RuntimeException(e)
            }
            catch (e: TemplateException) {
                throw RuntimeException(e)
            }
        }

        protected fun exportFreemarkerToString(
            ftlTemplate: String,
            templateData: Map<String, Any>
        ): String {
            try {
                StringWriter().use { out ->
                    export(ftlTemplate, templateData, out)
                    return out.toString()
                }
            }
            catch (e: IOException) {
                throw RuntimeException(e)
            }
            catch (e: TemplateException) {
                throw RuntimeException(e)
            }
        }

        @Throws(IOException::class, TemplateException::class)
        private fun export(ftlTemplate: String, templateData: Map<String, Any>, out: Writer) {
            val configuration = Configuration(Version(FREEMARKER_VERSION))
            configuration.setClassForTemplateLoading(FreemarkerTemplate::class.java, "/")
            configuration.defaultEncoding = StandardCharsets.UTF_8.displayName()

            val template = configuration.getTemplate(ftlTemplate)
            template.process(templateData, out)
            out.flush()
        }
    }
}
