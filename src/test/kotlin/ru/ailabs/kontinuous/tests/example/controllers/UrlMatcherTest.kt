package ru.ailabs.kontinuous.tests.example.controllers

import org.junit.Test
import ru.ailabs.kontinuous.controller.UrlMatcher
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 30.01.13
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */

class UrlMatcherTest {

    Test fun unmatchedUrl() : Unit {
        val matcher = UrlMatcher("/path/to/controller")
        val result = matcher.match("/path/to/another/controller")
        assertEquals(false, result.first)
        assertNull(result.second)

    }

    Test fun urlWithoutParams() : Unit {
        val matcher = UrlMatcher("/path/to/controller")
        val result = matcher.match("/path/to/controller")
        assertEquals(true, result.first)
        assertEquals(true, result.second!!.isEmpty() )

    }

    Test fun urlWithOneParam() : Unit {
        val matcher = UrlMatcher("/path/to/entity/:id")
        val result = matcher.match("/path/to/entity/100")
        assertEquals(true, result.first)
        assertEquals(false, result.second!!.isEmpty() )
        assertEquals(true, result.second!!.containsKey("id") )
        assertEquals("100", result.second!!.get("id") )
    }

    Test fun urlWithAnyParams() : Unit {
        val matcher = UrlMatcher("/path/:app/entity/:id")
        val result = matcher.match("/path/1/entity/100")
        assertEquals(true, result.first)
        assertEquals(false, result.second!!.isEmpty() )
        assertEquals(2, result.second!!.size() )
        assertEquals(true, result.second!!.containsKey("id") )
        assertEquals("100", result.second!!.get("id") )
        assertEquals(true, result.second!!.containsKey("app") )
        assertEquals("1", result.second!!.get("app") )
    }
}