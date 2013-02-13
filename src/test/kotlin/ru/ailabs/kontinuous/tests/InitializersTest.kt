package ru.ailabs.kontinuous.tests

import kotlin.test.assertEquals
import org.junit.Test
import ru.ailabs.kontinuous.annotation.initializers
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.initializer.InitializersBase
import ru.ailabs.kontinuous.initializer.Initializer
import org.mockito.Mockito.mock
import org.mockito.Mockito
import ru.ailabs.kontinuous.annotation.AnnotationScanner
import org.mockito.Mock
import kotlin.test.assertTrue
import kotlin.test.assertFalse

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

initializers class AnyInitializers : InitializersBase {
    override fun init(val app: Application) {
        ExampleSession.initalize()
    }
}

class InitializersTest {

    Test fun testInitializer() : Unit {
        val annotationScannerMock = mock(javaClass<AnnotationScanner>())!!
        Mockito.`when`(annotationScannerMock.scan(javaClass<initializers>() as java.lang.Class<Annotation>))!!.thenReturn(setOf(javaClass<AnyInitializers>()))

        assertFalse(ExampleSession.initialized)
        Application(annotationScannerMock)
        assertTrue(ExampleSession.initialized)
    }
}