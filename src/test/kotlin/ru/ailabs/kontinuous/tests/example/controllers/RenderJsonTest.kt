package ru.ailabs.kontinuous.tests.example.controllers

import org.junit.Test
import ru.ailabs.kontinuous.controller.helper.render_json
import kotlin.test.assertEquals

/**
 * User: andrew
 * Date: 19.02.13
 * Time: 8:49
 */

class RenderJsonTest {

    Test fun renderJson() {
        val result = render_json(hashMapOf(Pair("obj", "val"), Pair("obj2", hashMapOf(Pair("obj", "val")))))
        assertEquals("""{"obj":"val","obj2":{"obj":"val"}}""", result)
    }
}