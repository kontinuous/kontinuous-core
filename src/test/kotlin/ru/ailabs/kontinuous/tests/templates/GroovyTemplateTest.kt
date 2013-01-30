package ru.ailabs.kontinuous.tests.templates

import org.junit.Test
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.templates.GroovyTemplateRenderer

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

    object TemplateHelper {
        fun capitalize(value: String) : String {
            return value.capitalize()
        }
    }

    Test fun verifyJavaStaticImports() {
        val groovyTemplate = "<% import static ru.ailabs.kontinuous.tests.templates.StaticHelper.capitalize %>Hello \${capitalize(name)}"

        val templateParam: Map<String, *> = hashMapOf("name" to "kontinuous")

        val out = GroovyTemplateRenderer.render(groovyTemplate, templateParam)

        assertEquals("Hello Kontinuous", out)
    }

    Test fun verifyKotlinObjectImport() {
        val groovyTemplate = "<% import static ru.ailabs.kontinuous.tests.templates.GroovyTemplateTest.TemplateHelper.\$instance as helper %>Hello \${helper.capitalize(name)}"

        val templateParam: Map<String, *> = hashMapOf("name" to "kontinuous")

        val out = GroovyTemplateRenderer.render(groovyTemplate, templateParam)

        assertEquals("Hello Kontinuous", out)
    }
}
