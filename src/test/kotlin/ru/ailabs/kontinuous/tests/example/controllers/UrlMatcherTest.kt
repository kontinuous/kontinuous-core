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
        assertEquals(false, result.matched)
    }

    Test fun urlWithoutParams() : Unit {
        val matcher = UrlMatcher("/path/to/controller")
        val result = matcher.match("/path/to/controller")
        assertEquals(true, result.matched)

    }

    Test fun urlWithOneParam() : Unit {
        val matcher = UrlMatcher("/path/to/entity/:id")
        val result = matcher.match("/path/to/entity/100")
        assertEquals(true, result.matched)
        assertEquals(false, result.result.isEmpty() )
        assertEquals(true, result.result.containsKey("id") )
        assertEquals("100", result.result.get("id") )
    }

    Test fun urlWithAnyParams() : Unit {
        val matcher = UrlMatcher("/path/:app/entity/:id")
        val result = matcher.match("/path/1/entity/100")
        assertEquals(true, result.matched)
        assertEquals(false, result.result.isEmpty() )
        assertEquals(true, result.result.containsKey("id") )
        assertEquals("100", result.result.get("id") )
        assertEquals(true, result.result.containsKey("app") )
        assertEquals("1", result.result.get("app") )
    }

    Test fun urlWithSParam() : Unit {
        val matcher = UrlMatcher("/path/to/entity/:post")
        val result = matcher.match("/path/to/entity/100")
        assertEquals(true, result.matched)
        assertEquals(false, result.result.isEmpty() )
        assertEquals(true, result.result.containsKey("post") )
        assertEquals("100", result.result.get("post") )
    }

    Test fun urlWithParam() : Unit {
        val matcher = UrlMatcher("/post/:name")
        val result = matcher.match("/post/megapost")
        assertEquals(true, result.matched)
        assertEquals(false, result.result.isEmpty() )
        assertEquals(true, result.result.containsKey("name") )
        assertEquals("megapost", result.result.get("name") )
    }

    Test fun greedyUrlParameter() {
        val matcher = UrlMatcher("/post/*file")
        val result = matcher.match("/post/path/to/megafile.js")
        assertEquals(true, result.matched)
        assertEquals(false, result.result.isEmpty() )
        assertEquals(true, result.result.containsKey("file") )
        assertEquals("path/to/megafile.js", result.result.get("file") )
    }
    Test fun urlWithBothParameterType() {
        val matcher = UrlMatcher("/post/:id/path/*file")
        val result = matcher.match("/post/13/path/to/megafile.js")
        assertEquals(true, result.matched)
        assertEquals(false, result.result.isEmpty() )
        assertEquals(true, result.result.containsKey("id") )
        assertEquals(true, result.result.containsKey("file") )
        assertEquals("to/megafile.js", result.result.get("file") )
        assertEquals("13", result.result.get("id") )
    }
}