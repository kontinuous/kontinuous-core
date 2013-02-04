package ru.ailabs.kontinuous.tests

import kotlin.test.assertEquals
import org.junit.Test
import ru.ailabs.kontinuous.annotation.initializers
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.initializer.InitializersBase
import ru.ailabs.kontinuous.initializer.Initializer

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 03.02.13
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */

object ExampleSession {

    var session = "Not initialized"

    fun initalize(val app: Application)  {
        session = "Initialized"
    }
}


initializers class AnyInitializers : InitializersBase {

    override fun init(val app: Application) {
        ExampleSession.initalize(app)
    }
}

class InitializersTest {

    Test fun testInitializer() : Unit {
        assertEquals("Not initialized", ExampleSession.session)
        Application()
        assertEquals("Initialized", ExampleSession.session)
    }
}