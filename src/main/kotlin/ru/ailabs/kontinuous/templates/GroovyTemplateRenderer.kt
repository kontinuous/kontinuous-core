package ru.ailabs.kontinuous.templates

import groovy.text.SimpleTemplateEngine

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/29/13
 * Time: 11:03 PM
 */
object GroovyTemplateRenderer {

    fun render(groovyTemplate : String, templateParam : Map<String, *>) : String {
        val engine = SimpleTemplateEngine()
        val template = engine.createTemplate(groovyTemplate)?.make(templateParam)
        return template.toString()
    }
}