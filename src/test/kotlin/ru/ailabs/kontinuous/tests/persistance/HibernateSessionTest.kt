package ru.ailabs.kontinuous.tests.persistance

import org.junit.Test
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.persistance.HibernateSession
import ru.ailabs.kontinuous.initializer.Application
import org.mockito.Mockito.*
import org.mockito.Mockito
import java.util.Properties
import ru.ailabs.kontinuous.annotation.AnnotationScanner
import ru.ailabs.kontinuous.annotation.initializers
import ru.ailabs.kontinuous.HibernateInitializer
import kotlin.test.assertTrue
import kotlin.test.assertFalse

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 08.02.13
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */

class HibernateSessionTest {

    Test fun testSessionInitialization() : Unit {
        val annotationScannerMock = mock(javaClass<AnnotationScanner>())!!
        Mockito.`when`(annotationScannerMock.scan(javaClass<initializers>() as java.lang.Class<Annotation>))!!.thenReturn(setOf(javaClass<HibernateInitializer>()))

        assertFalse(HibernateSession.initialized())
        Application(annotationScannerMock)
        assertTrue(HibernateSession.initialized())
    }
}