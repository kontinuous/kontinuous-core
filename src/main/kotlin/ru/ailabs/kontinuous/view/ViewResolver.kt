package ru.ailabs.kontinuous.view

import org.apache.velocity.app.Velocity
import org.apache.velocity.VelocityContext
import java.io.StringWriter
import java.util.Properties

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 4:36 PM
 */
class ViewResolver {

    fun resolveView(params: Map<String, *>, viewName: String) : String {
        val props = Properties()
        props.setProperty("resource.loader", "class")
        props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader")

        Velocity.init(props)
        val context = VelocityContext(params)

        val template = Velocity.getTemplate(viewName);
        val sw = StringWriter();

        template?.merge( context, sw );

        return sw.toString()
    }
}
