package ru.ailabs.kontinuous.tests.example.controllers

import org.junit.Test
import example.controllers.HelloController
import kotlin.test.assertTrue
import kotlin.test.assertNotNull
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.controller.Context


/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 12:49 PM
 */
class ControllerTest {
    Test fun controllerShouldReturnPair() {
        val action = HelloController.index
        assertNotNull(action)
        assertTrue(action.handle(Context(hashMapOf())) is Pair<Map<String, *>, String>)
    }

    Test fun controllerShouldReturnView() {
        val response = HelloController.index.handle(Context(hashMapOf()))
        assertEquals("indexView" , response.component2())
    }
}


