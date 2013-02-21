package ru.ailabs.kontinuous.tests.persistance

import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.Test
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.persistance.HibernateSession

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 08.02.13
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */

class HibernateSessionTest {

    Test fun testSessionInitialization() : Unit {
        assertFalse(HibernateSession.initialized())
        Application()
        assertTrue(HibernateSession.initialized())
    }
}