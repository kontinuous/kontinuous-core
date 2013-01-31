package ru.ailabs.kontinuous.view

import org.apache.velocity.app.Velocity
import org.apache.velocity.VelocityContext
import java.io.StringWriter
import java.util.Properties
import ru.ailabs.kontinuous.templates.GroovyTemplateRenderer

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 4:36 PM
 */
class ViewResolver {

    var defaultLayout = "view/layout/application.tmpl.html"

    fun resolveView(params: Map<String, Any>, viewName: String) : String {

        val renderer = GroovyTemplateRenderer()

        if(this.javaClass.getClassLoader()!!.getResourceAsStream(defaultLayout) != null) {
            return renderer.renderWithLayout(viewName, defaultLayout, params)
        }

        return renderer.renderTemplateFile(viewName, params)
    }
}
