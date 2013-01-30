package ru.ailabs.kontinuous.tests.templates

import org.junit.Test
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.templates.GroovyTemplateRenderer
import java.util.HashMap

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

    Test fun renderWithLayout() {
        val layout = "outer text \${content}"
        val template = "inner text \${name}"

        val templateParam : Map<String, *> = hashMapOf("name" to "kontinuous")

        val templateRendered = GroovyTemplateRenderer.render(template, templateParam)
        val layoutParam = HashMap<String, Any>()
        for((key, value) in templateParam) {
            layoutParam.put(key, value!!)
        }
        layoutParam.put("content", templateRendered.toString())
        val out = GroovyTemplateRenderer.render(layout, layoutParam)

        assertEquals("outer text inner text kontinuous", out)
    }
}
