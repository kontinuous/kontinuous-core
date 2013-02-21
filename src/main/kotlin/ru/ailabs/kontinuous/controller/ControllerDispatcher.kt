/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.controller

import java.util.HashMap
import org.reflections.Reflections
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.view.ViewResolver
import java.util.HashSet
import ru.ailabs.kontinuous.logger.LoggerFactory
import ru.ailabs.kontinuous.configuration.Method

class ControllerDispatcher(val routes: Map<String, Set<Method>>) {

    val logger = LoggerFactory.getLogger("ru.ailabs.kontinuous.controller.ControllerDispatcher")

    val viewResolver = ViewResolver()

    private fun findAction(val path: String, val method: String): ActionHandler {
        val mapping = routes.get(method.toUpperCase())
        return if (mapping != null) {
            val pair = mapping map { Pair(it.matcher.match(path), it.action) } find {it.first.matched}

            if(pair != null) {
                val pathNamedParam = pair.first.result
                ActionHandler(pair.second, pathNamedParam)
            } else {
                ActionHandler(Action404, hashMapOf("url" to path))
            }
        } else {
            logger.error("undefined method ${method} on path ${path}");
            ActionHandler(Action404, hashMapOf("url" to path)) //todo undefined method
        }
    }

    fun findActionHandler(val requestHeader: RequestHeader): ActionHandler {
        val path = requestHeader.path
        return findAction(path, requestHeader.method.getName()!!)
    }
}