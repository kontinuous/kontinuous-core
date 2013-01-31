package ru.ailabs.kontinuous.tests.view

import ru.ailabs.kontinuous.view.ViewResolver
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 4:40 PM
 */
class ViewResolverTest {
    Test fun shouldRenderWithoutLayout() {
        val viewResolver = ViewResolver()

        val name = "Aleksandr Khamutov"

        assertEquals("Hello ${name}!", viewResolver.resolveView(hashMapOf("name" to name), "views/hello/index.tmpl.html"))
    }

    Test fun shouldRenderWithLayout() {
        val viewResolver = ViewResolver()
        viewResolver.defaultLayout = "views/hello/simple_layout.tmpl.html"

        val name = "Aleksandr Khamutov"

        assertEquals("simple Hello ${name}!", viewResolver.resolveView(hashMapOf("name" to name), "views/hello/index.tmpl.html"))
    }
}
