package ru.ailabs.kontinuous.tests

import kotlin.test.assertEquals
import org.junit.Test
import ru.ailabs.kontinuous.annotation.initializers
import ru.ailabs.kontinuous.initializer.Application

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 03.02.13
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */

object ExampleSession {

    var session = "Not initialized"

    fun initialize() {
        session = "Initialized"
    }
}


initializers class Initializers {

    val initializers = hashSetOf<() -> Unit>(
            {ExampleSession.initialize()}
    )
}

class InitializersTest {

    Test fun testInitializer() : Unit {
        assertEquals("Not initialized", ExampleSession.session)
        Application()
        assertEquals("Initialized", ExampleSession.session)
    }
}