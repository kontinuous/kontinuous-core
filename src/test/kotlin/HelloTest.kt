package ru.ailabs.kontinuous.tests;

import junit.framework.TestCase
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.getHelloString

class HelloTest : TestCase() {
    fun testAssert() : Unit {
        assertEquals("Hello, world!", getHelloString())
    }
}