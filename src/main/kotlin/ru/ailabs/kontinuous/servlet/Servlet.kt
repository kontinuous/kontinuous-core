package ru.ailabs.kontinuous.servletkotlin

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import ru.ailabs.kontinuous.dispatcher.ControllerDispatcher

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 27.01.13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public open class Servlet() : HttpServlet() {
    private var greeting : String? = "Hello mad World!!!"
    protected override fun doGet(request : HttpServletRequest?, response : HttpServletResponse?) : Unit {

        var dispather: ControllerDispatcher = ControllerDispatcher()
        var path : String = dispather.dispatch(request?.getRequestURI()!!)
        response?.setContentType("text/html")
        response?.setStatus(HttpServletResponse.SC_OK)
        response?.getWriter()?.println("<h1>" + path + "</h1>")
        response?.getWriter()?.println("1111session=" + request?.getSession(true)?.getId())
        response?.getWriter()?.println("context path = [" + request?.getRequestURI() + "]")
    }

}
