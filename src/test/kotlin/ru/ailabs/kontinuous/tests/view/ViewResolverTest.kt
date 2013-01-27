package ru.ailabs.kontinuous.tests.view

import ru.ailabs.kontinuous.view.ViewResolver
import org.junit.Test

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 4:40 PM
 */

class ViewResolverTest {
    Test fun shouldReturnText() {
        val viewResolver = ViewResolver()

        println(viewResolver.resolveView(hashMapOf("name" to "Aleksandr Khamutov"), "views/hello/index.vm"))
    }
}
