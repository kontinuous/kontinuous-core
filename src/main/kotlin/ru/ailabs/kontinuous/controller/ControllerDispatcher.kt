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
import ru.ailabs.kontinuous.annotation.path
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.view.ViewResolver
import java.util.HashSet
import ru.ailabs.kontinuous.annotation.routes
import ru.ailabs.kontinuous.logger.LoggerFactory

class ControllerDispatcher {

    val logger = LoggerFactory.getLogger("ru.ailabs.kontinuous.controller.ControllerDispatcher")

    val routes = HashSet<Pair<UrlMatcher, Action>>();

    {
        for (cls in scanForRoutes()!!.toCollection()) {
            val inst = cls.newInstance();
            for (fld in cls.getDeclaredFields()) {
                for (ann in fld.getAnnotations()) {
                    if (ann is path) {
                        fld.setAccessible(true)
                        routes.add(Pair(UrlMatcher(ann.path), fld.get(inst) as Action))
                        logger.debug("add route ${ann.path} to ${fld.get(inst)}");
                    }
                }
            }
        }
    }

    val viewResolver = ViewResolver()

    fun scanForRoutes(): jet.MutableSet<java.lang.Class<out jet.Any?>>? {
        return Reflections("").getTypesAnnotatedWith(javaClass<routes>())
    }

    private fun findAction(val path: String): ActionHandler {
        val pair = routes find { it -> it.first.match(path).first }

        return if(pair != null) {
            val pathNamedParam = pair.first.match(path).second
            ActionHandler(pair.second, pathNamedParam)
        } else {
            ActionHandler(Action404, hashMapOf("url" to path))
        }
    }

    fun findActionHandler(val requestHeader: RequestHeader): ActionHandler {
        val path = requestHeader.path
        return findAction(path)
    }
}