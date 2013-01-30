package ru.ailabs.kontinuous.tests.templates

import org.junit.Test
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.templates.GroovyTemplateRenderer
import java.util.HashMap
import org.junit.Before

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/29/13
 * Time: 10:45 PM
 */
fun capitalize(value: String): String  {
    return value.capitalize();
}

class GroovyTemplateTest {

    val renderer = GroovyTemplateRenderer()

    Test fun verifyRendering() {
        val groovyTemplate = "Hello \${name}"

        val templateParam: Map<String, Any> = hashMapOf("name" to "Kontinuous")

        val out = renderer.render(groovyTemplate, templateParam)

        assertEquals("Hello Kontinuous", out)
    }

    object TemplateHelper {
        fun capitalize(value: String) : String {
            return value.capitalize()
        }
    }

    Test fun verifyJavaStaticImports() {
        val groovyTemplate = "<% import static ru.ailabs.kontinuous.tests.templates.StaticHelper.capitalize %>Hello \${capitalize(name)}"

        val templateParam: Map<String, Any> = hashMapOf("name" to "kontinuous")

        val out = renderer.render(groovyTemplate, templateParam)

        assertEquals("Hello Kontinuous", out)
    }

    Test fun verifyKotlinObjectImport() {
        val groovyTemplate = "<% import static ru.ailabs.kontinuous.tests.templates.GroovyTemplateTest.TemplateHelper.\$instance as helper %>Hello \${helper.capitalize(name)}"

        val templateParam: Map<String, Any> = hashMapOf("name" to "kontinuous")

        val out = renderer.render(groovyTemplate, templateParam)

        assertEquals("Hello Kontinuous", out)
    }

    Test fun verifyKotlinFunctionImport() {
        val groovyTemplate = "<% import static ru.ailabs.kontinuous.tests.templates.namespace.capitalize %>Hello \${capitalize(name)}"

        val templateParam: Map<String, Any> = hashMapOf("name" to "kontinuous")

        val out = renderer.render(groovyTemplate, templateParam)
        assertEquals("Hello Kontinuous", out)
    }

    Test fun renderWithLayout() {
        val templateParam : Map<String, Any> = hashMapOf("name" to "kontinuous")

        val templateRendered = renderer.renderWithLayout("views/hello/inner.tmpl.html", "views/hello/hello_layout.tmpl.html", templateParam)

        val expectedResult = """<html>
  <head>
    <title>Default title</title>
  </head>
  <body>
  hello kontinuous
  </body>
</html>"""

        assertEquals(expectedResult, templateRendered)
    }

    Test fun renderWithLayoutAndLayoutArgs() {
        val templateParam : Map<String, Any> = hashMapOf("name" to "kontinuous", "title" to "Custom title")

        val templateRendered = renderer.renderWithLayout("views/hello/inner.tmpl.html", "views/hello/hello_layout.tmpl.html", templateParam)

        val expectedResult = """<html>
  <head>
    <title>Custom title</title>
  </head>
  <body>
  hello kontinuous
  </body>
</html>"""

        assertEquals(expectedResult, templateRendered)
    }
}
