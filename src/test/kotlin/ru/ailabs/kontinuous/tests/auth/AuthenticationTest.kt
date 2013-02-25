/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.tests.auth

import kotlin.test.assertEquals
import org.jboss.netty.handler.codec.http.HttpMethod
import org.jboss.netty.handler.codec.http.HttpVersion
import org.junit.Test
import ru.ailabs.kontinuous.auth.authenticated
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Action404
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.RequestHeader
import ru.ailabs.kontinuous.controller.helper.render
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.controller.Redirect
import kotlin.test.assertTrue
import ru.ailabs.kontinuous.controller.Context
import ru.ailabs.kontinuous.controller.HttpHeaderNames
import ru.ailabs.kontinuous.controller.Cookies
import ru.ailabs.kontinuous.controller.Cookie
import org.hibernate.Session
import org.mockito.Mockito.mock

object Controller {
    val secured = Action ({
        Ok(render("views/hello/index.tmpl.html", hashMapOf("name" to "name")))
    })
}

class TestApplication: Application() {
    {
        new {
            authenticated("/login"){
                get("/secured", Controller.secured)
            }
        }
        initialize()
    }
}


class ControllerDispatcherTest {

    val dispatcher = TestApplication().dispatcher

    Test fun shouldRedirectToLoginPage(): Unit {
        val request = RequestHeader(
                keepAlive = true,
                requestProtocolVersion = HttpVersion.HTTP_1_1,
                uri = "/secured",
                path = "/secured",
                method = HttpMethod.GET,
                parameters = hashMapOf(),
                headers = listOf(),
                cookies = hashMapOf()
        )
        val context = Context(hashMapOf(), mock(javaClass<Session>())!!, ByteArray(1), request)
        val actionHandler = dispatcher.findActionHandler(request)
        val result = actionHandler.action.handler(context)
        assertTrue(result is Redirect)
        assertEquals("/login", result.header.headers[HttpHeaderNames.LOCATION])
    }

    Test fun shouldInvokeAction(): Unit {
        val request = RequestHeader(
                keepAlive = true,
                requestProtocolVersion = HttpVersion.HTTP_1_1,
                uri = "/secured",
                path = "/secured",
                method = HttpMethod.GET,
                parameters = hashMapOf(),
                headers = listOf(),
                cookies = hashMapOf(Cookies.userId to Cookie(Cookies.userId, "123"))
        )
        val context = Context(hashMapOf(), mock(javaClass<Session>())!!, ByteArray(1), request)
        val actionHandler = dispatcher.findActionHandler(request)
        val result = actionHandler.action.handler(context)
        assertTrue(result is Ok)
    }
}