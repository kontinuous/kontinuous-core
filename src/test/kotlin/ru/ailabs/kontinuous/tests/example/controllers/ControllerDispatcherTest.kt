/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.tests.example.controllers

import kotlin.test.assertEquals
import org.jboss.netty.handler.codec.http.HttpMethod
import org.jboss.netty.handler.codec.http.HttpVersion
import org.junit.Test
import ru.ailabs.kontinuous.configuration.Configuration
import ru.ailabs.kontinuous.configuration.configuration
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Action404
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.RequestHeader
import ru.ailabs.kontinuous.controller.helper.render
import ru.ailabs.kontinuous.initializer.Application

object Controller {
    val index = Action ({
        Ok(render("Hello index!", hashMapOf()))
    })

    val post = Action ({
        Ok(render("Hello post!", hashMapOf()))
    })

    val show_post = Action ({ context ->
        Ok("/post/megapost")
    })

    val post_m = Action ({
        Ok(render("Hello post method!", hashMapOf()))
    })
}

class TestApplication: Application() {
    override fun configure(init: Configuration.() -> Unit): Configuration
            = configuration {
        get("/", Controller.index)
        post("/post", Controller.post_m)
        get("/post", Controller.post)
        get("/post/:name", Controller.show_post)
    }
}

class ControllerDispatcherTest {

    val dispatcher = TestApplication().dispatcher

    Test fun shouldReturnRootHandler(): Unit {
        val request = RequestHeader(
                keepAlive = true,
                requestProtocolVersion = HttpVersion.HTTP_1_1,
                uri = "/",
                path = "/",
                method = HttpMethod.GET,
                parameters = hashMapOf(),
                headers = listOf()
        )
        val actionHandler = dispatcher.findActionHandler(request)
        assertEquals(Controller.index, actionHandler.action)
    }

    Test fun shouldReturnRegularPathHandler(): Unit {
        val request = RequestHeader(
                keepAlive = true,
                requestProtocolVersion = HttpVersion.HTTP_1_1,
                uri = "/post",
                path = "/post",
                method = HttpMethod.GET,
                parameters = hashMapOf(),
                headers = listOf()
        )
        val actionHandler = dispatcher.findActionHandler(request)
        assertEquals(Controller.post, actionHandler.action)
    }

    Test fun shouldReturn404(): Unit {
        val request = RequestHeader(
                keepAlive = true,
                requestProtocolVersion = HttpVersion.HTTP_1_1,
                uri = "/asd",
                path = "/asd",
                method = HttpMethod.GET,
                parameters = hashMapOf(),
                headers = listOf()
        )
        val actionHandler = dispatcher.findActionHandler(request)
        assertEquals(Action404, actionHandler.action)
    }

    Test fun shouldReturnNamedPathHandler(): Unit {
        val request = RequestHeader(
                keepAlive = true,
                requestProtocolVersion = HttpVersion.HTTP_1_1,
                uri = "/post/megapost",
                path = "/post/megapost",
                method = HttpMethod.GET,
                parameters = hashMapOf(),
                headers = listOf()
        )
        val actionHandler = dispatcher.findActionHandler(request)
        assertEquals(Controller.show_post, actionHandler.action)
        assertEquals("megapost", actionHandler.namedParams["name"])
    }
    Test fun shouldReturnNamedPathHandlerWithMethodPost(): Unit {
        val request = RequestHeader(
                keepAlive = true,
                requestProtocolVersion = HttpVersion.HTTP_1_1,
                uri = "/post",
                path = "/post",
                method = HttpMethod.POST,
                parameters = hashMapOf(),
                headers = listOf()
        )
        val actionHandler = dispatcher.findActionHandler(request)
        assertEquals(Controller.post_m, actionHandler.action)
    }
}