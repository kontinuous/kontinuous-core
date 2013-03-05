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
import ru.ailabs.kontinuous.controller.KontinuousSession

/**
 * User: andrew
 * Date: 22.02.13
 * Time: 13:37
 */

abstract class AuthenticatedConfiguration(val routes: HashMap<String, List<Method>>) {

    fun get(path: String, action: Action) {
        val route = Get(path, wrap(action))
        routes.put(route.method, routes.getOrPut(route.method, { listOf<Method>() }) + route)
    }

    fun post(path: String, action: Action) {
        val route = Post(path, wrap(action))
        routes.put(route.method, routes.getOrPut(route.method, { listOf<Method>() }) + route)
    }

    protected fun wrap(action: Action) : Action =
        Action({ ctx ->
            if(!ctx.userSession.isAuthenticated())
                loginAction.handler(ctx)//Redirect(login)
            else
                action.handler(ctx)
        })

    protected abstract val loginAction: Action
}

class RedirectAuthenticatedConfiguration(redirect: String, routes: HashMap<String, List<Method>>) :
AuthenticatedConfiguration(routes) {
    protected override val loginAction: Action = Action({
        Redirect(redirect)
    })
}

class ActionAuthenticatedConfiguration(action: Action, routes: HashMap<String, List<Method>>) :
AuthenticatedConfiguration(routes) {
    protected override val loginAction: Action = action
}


public fun Configuration.authenticated(redirect: String, init: AuthenticatedConfiguration.() -> Unit) {
    val conf = RedirectAuthenticatedConfiguration(redirect, routes)
    conf.init()
}

public fun Configuration.authenticated(action: Action, init: AuthenticatedConfiguration.() -> Unit) {
    val conf = ActionAuthenticatedConfiguration(action, routes)
    conf.init()
}

public val paramUserId: String = "USER_ID"

fun KontinuousSession.authenticate(userId: String) {
    set(paramUserId, userId)
}

fun KontinuousSession.unauthenticate() : Boolean {
    return remove(paramUserId) != null
}

fun KontinuousSession.isAuthenticated() : Boolean {
    return get(paramUserId) != null
}

fun KontinuousSession.getUserId() : String? {
    return get(paramUserId)
}
