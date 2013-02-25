package ru.ailabs.kontinuous.auth

import ru.ailabs.kontinuous.configuration.Configuration
import java.util.HashMap
import java.util.HashSet
import ru.ailabs.kontinuous.configuration.Method
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.configuration.Get
import ru.ailabs.kontinuous.configuration.Post
import ru.ailabs.kontinuous.controller.Redirect
import ru.ailabs.kontinuous.controller.Cookies

/**
 * User: andrew
 * Date: 22.02.13
 * Time: 13:37
 */

class AuthenticatedConfiguration(val login: String, val routes: HashMap<String, HashSet<Method>>) {

    fun get(path: String, action: Action) {
        val route = Get(path, wrap(action))
        routes.getOrPut(route.method, { hashSetOf<Method>() }).add(route)
    }

    fun post(path: String, action: Action) {
        val route = Post(path, wrap(action))
        routes.getOrPut(route.method, { hashSetOf<Method>() }).add(route)
    }

    private fun wrap(action: Action) : Action =
        Action({ ctx ->
            if(ctx.requestHeaders.cookies.get(Cookies.userId) == null)
                Redirect(login)
            else
                action.handler(ctx)
        })
}


fun Configuration.authenticated(login: String, init: AuthenticatedConfiguration.() -> Unit) {
    val conf = AuthenticatedConfiguration(login, routes)
    conf.init()
}