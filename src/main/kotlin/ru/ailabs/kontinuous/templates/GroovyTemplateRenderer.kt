package ru.ailabs.kontinuous.templates

import groovy.text.SimpleTemplateEngine
import java.io.InputStreamReader
import java.util.HashMap

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/29/13
 * Time: 11:03 PM
 */
object GroovyTemplateRenderer {

    fun render(groovyTemplate: String, templateParam: Map<String, Any>): String {
        val engine = SimpleTemplateEngine()
        val template = engine.createTemplate(groovyTemplate)?.make(templateParam)
        return template.toString()
    }

    fun renderTemplateFile(templateFileName: String, templateParam: Map<String, Any>): String {
        val engine = SimpleTemplateEngine()

        val templateInputStream = this.javaClass.getClassLoader()!!.getResourceAsStream(templateFileName)

        if(templateInputStream != null) {
            val templateReader = InputStreamReader(templateInputStream)

            val template = engine.createTemplate(templateReader)?.make(templateParam)

            return template.toString()
        } else {
            return "template ${templateFileName} not found"
        }
    }

    fun renderWithLayout(templateName: String, layoutName: String, templateParam: Map<String, Any>): String {
        val templateRendered = renderTemplateFile(templateName, templateParam)

        val layoutParam = HashMap<String, Any>()
        for((key, value) in templateParam) {
            layoutParam.put(key, value!!)
        }

        layoutParam.put("content", templateRendered.toString())

        val layoutRendered = renderTemplateFile(layoutName, layoutParam)

        return layoutRendered
    }
}