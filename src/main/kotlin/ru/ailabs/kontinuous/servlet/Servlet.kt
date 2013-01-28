package ru.ailabs.kontinuous.servlet

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
    private var greeting = "Hello mad World!!!"
    protected override fun doGet(req : HttpServletRequest?, resp : HttpServletResponse?) : Unit {

        var dispather: ControllerDispatcher = ControllerDispatcher()
        var path : String = dispather.dispatch(req?.getRequestURI()!!)
        resp?.setContentType("text/html")
        resp?.setStatus(HttpServletResponse.SC_OK)
        resp?.getWriter()?.println("<h1>" + path + "</h1>")
        resp?.getWriter()?.println("1111session=" + req?.getSession(true)?.getId())
        resp?.getWriter()?.println("context path = [" + req?.getRequestURI() + "]")
    }

}
