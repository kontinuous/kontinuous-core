/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.tests.auth

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.hibernate.Session
import org.jboss.netty.handler.codec.http.HttpMethod
import org.jboss.netty.handler.codec.http.HttpVersion
import org.junit.Test
import org.mockito.Mockito.mock
import ru.ailabs.kontinuous.auth.authenticated
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Context
import ru.ailabs.kontinuous.controller.Cookie
import ru.ailabs.kontinuous.controller.Cookies
import ru.ailabs.kontinuous.controller.HttpHeaderNames
import ru.ailabs.kontinuous.controller.KontinuousSession
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.Redirect
import ru.ailabs.kontinuous.controller.RequestHeader
import ru.ailabs.kontinuous.controller.helper.render
import ru.ailabs.kontinuous.initializer.Application

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


class AuthenticationTest {

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
        val context = Context(hashMapOf<String, String>(), mock(javaClass<Session>())!!, KontinuousSession(), ByteArray(1), request)
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
                cookies = hashMapOf()
        )
        val context = Context(hashMapOf<String, String>(), mock(javaClass<Session>())!!, KontinuousSession(hashMapOf(Cookies.userId to "123")), ByteArray(1), request)
        val actionHandler = dispatcher.findActionHandler(request)
        val result = actionHandler.action.handler(context)
        assertTrue(result is Ok)
    }
}