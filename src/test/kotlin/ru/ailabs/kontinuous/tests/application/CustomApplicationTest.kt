package ru.ailabs.kontinuous.tests.application

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.Test
import ru.ailabs.kontinuous.configuration.Configuration
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.configuration.configuration
import kotlin.test.assertNotNull
import ru.ailabs.kontinuous.initializer.ApplicationDiscovery

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 03.02.13
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */

object ExampleSession {
    var initialized = false

    fun initalize()  {
        initialized = true
    }
}

class CustomApplication : Application() {
    override fun configure(init: Configuration.() -> Unit): Configuration
            = configuration {
            initialize { ExampleSession.initalize() }
        }
}

class CustomApplicationTest {

    Test fun testApplicationDiscovery() : Unit {
        assertFalse(ExampleSession.initialized)
        val app = Application.create(object : ApplicationDiscovery {
            override fun find(): Class<out Application> {
                return javaClass<CustomApplication>()
            }
        })
        assertNotNull(Application.instance)
        assertTrue(app is CustomApplication)
        assertTrue(ExampleSession.initialized)
    }
}