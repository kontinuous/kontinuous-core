package ru.ailabs.kontinuous.servletkotlin

import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHolder

fun main(args : Array<String>) {

    var server : Server = Server(8060)
    var context : ServletContextHandler = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.setContextPath("/")
    server.setHandler(context)
    var servlet : Servlet? = Servlet()
    context.addServlet(ServletHolder(servlet), "/hello")
    server.start()
    server.join()
}


