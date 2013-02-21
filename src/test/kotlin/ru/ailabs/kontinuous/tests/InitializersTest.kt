package ru.ailabs.kontinuous.tests

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.Test
import ru.ailabs.kontinuous.configuration.Configuration
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.configuration.configuration

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

class TestApplication : Application() {
    {
        new {
            initialize { ExampleSession.initalize() }
        }
    }
}

class InitializersTest {

    Test fun testInitializer() : Unit {
        assertFalse(ExampleSession.initialized)
        TestApplication().initialize()
        assertTrue(ExampleSession.initialized)
    }
}