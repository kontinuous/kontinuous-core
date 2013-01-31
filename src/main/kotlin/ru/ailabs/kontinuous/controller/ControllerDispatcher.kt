/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.controller

import javax.servlet.http.HttpServletRequest
import java.util.HashMap
import org.reflections.Reflections
import ru.ailabs.kontinuous.annotation.path
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.view.ViewResolver
import java.util.HashSet
import ru.ailabs.kontinuous.annotation.routes

class ControllerDispatcher() {

    val viewResolver = ViewResolver()

    val routes = HashSet<Pair<UrlMatcher, Action>>();
    {
        for (cls in scanForRoutes()!!.toCollection()) {
            val inst = cls!!.newInstance();
            for (fld in cls.getDeclaredFields()) {
                for (ann in fld.getAnnotations()) {
                    if (ann is path) {
                        fld.setAccessible(true)
                        routes.add(Pair(UrlMatcher(ann.path), fld.get(inst) as Action))
                    }
                }
            }
        }
    }

    fun scanForRoutes(): jet.MutableSet<java.lang.Class<out jet.Any?>?>? {
        return Reflections("").getTypesAnnotatedWith(javaClass<routes>())
    }

    fun dispatch(val url: String): String {
        val pair = routes find { it -> it.first.match(url).first }
        return if (pair != null) {
            val answer = pair.second.handle(Context(pair.first.match(url).second))
            return viewResolver.resolveView(answer.component1(), answer.component2())
        }
        else "No route found"
    }
}