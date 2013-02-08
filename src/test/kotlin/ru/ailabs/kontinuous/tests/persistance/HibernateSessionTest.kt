package ru.ailabs.kontinuous.tests.persistance

import org.junit.Test
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.persistance.HibernateSession
import ru.ailabs.kontinuous.initializer.Application

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 08.02.13
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */

class HibernateSessionTest {

    Test fun testSessionInitialization() : Unit {
        assertEquals(false, HibernateSession.initialized())
        Application()
        assertEquals(true, HibernateSession.initialized())
    }
}