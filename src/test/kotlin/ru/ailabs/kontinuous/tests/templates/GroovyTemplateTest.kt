package ru.ailabs.kontinuous.tests.templates

import org.junit.Test
import ru.ailabs.kontinuous.templates.GroovyTemplateRenderer
import kotlin.test.assertEquals

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/29/13
 * Time: 10:45 PM
 */
class GroovyTemplateTest {
    Test fun verifyRendering() {
        val groovyTemplate = "Hello \${name}"

        val templateParam: Map<String, *> = hashMapOf("name" to "Kontinuous")

        val out = GroovyTemplateRenderer.render(groovyTemplate, templateParam)

        assertEquals("Hello Kontinuous", out)
    }
}
