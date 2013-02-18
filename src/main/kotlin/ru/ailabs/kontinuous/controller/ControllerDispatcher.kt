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
import ru.ailabs.kontinuous.annotation.routes
import ru.ailabs.kontinuous.logger.LoggerFactory
import ru.ailabs.kontinuous.annotation.GET
import ru.ailabs.kontinuous.annotation.POST

class ControllerDispatcher {

    val logger = LoggerFactory.getLogger("ru.ailabs.kontinuous.controller.ControllerDispatcher")

    val routes = HashMap<String, HashSet<Pair<UrlMatcher, Action>>>();
//    val post = HashSet<Pair<UrlMatcher, Action>>();

    {
        for (cls in scanForRoutes()!!.toCollection()) {
            val inst = cls.newInstance();
            for (fld in cls.getDeclaredFields()) {
                for (ann in fld.getAnnotations()) {
                    if(ann is GET || ann is POST) {
                        val name = ann.annotationType().getSimpleName();
                        var mapping = routes.get(name);
                        if (mapping == null) {
                            mapping = HashSet<Pair<UrlMatcher, Action>>()
                            routes.put(name, mapping!!)
                        }
                        val path = if (ann is GET) ann.path else if (ann is POST) ann.path
                        fld.setAccessible(true)
                        mapping?.add(Pair(UrlMatcher(path as String), fld.get(inst) as Action))
                        logger.debug("add route ${path} to ${fld.get(inst)} method: ${name}");
                    }
                }
            }
        }
    }

    val viewResolver = ViewResolver()

    fun scanForRoutes(): jet.MutableSet<java.lang.Class<out jet.Any?>>? {
        return Reflections("").getTypesAnnotatedWith(javaClass<routes>())
    }

    private fun findAction(val path: String, val method: String): ActionHandler {
        val mapping = routes.get(method.toUpperCase())
        return if (mapping != null) {
            val pair = mapping find { it -> it.first.match(path).first }

            if(pair != null) {
                val pathNamedParam = pair.first.match(path).second
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