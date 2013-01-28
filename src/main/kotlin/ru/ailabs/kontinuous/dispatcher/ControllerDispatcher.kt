/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.dispatcher

import javax.servlet.http.HttpServletRequest
import java.util.HashMap
import org.reflections.Reflections
import ru.ailabs.kontinuous.annotation.AnnotationUtils
import ru.ailabs.kontinuous.annotation.path
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.view.ViewResolver

class ControllerDispatcher() {

    val viewResolver = ViewResolver()

    val routes = HashMap<String, Action>();
    {
        for (cls in AnnotationUtils.findRoutesClasses()!!.toCollection()) {
            val inst = cls!!.newInstance();
            for (fld in cls.getDeclaredFields()) {
                for (ann in fld.getAnnotations()) {
                    if (ann is path) {
                        fld.setAccessible(true)
                        routes.put(ann.path, fld.get(inst) as Action)
                    }
                }
            }
        }
    }

    fun dispatch(val url: String): String {
        val method = routes.get(url)
        return if (method != null) {
            val answer = method.handle("")
            return viewResolver.resolveView(answer.component1(), answer.component2())
        }
        else "No route found"
    }
}