package ru.ailabs.kontinuous.initializer

import org.reflections.Reflections
import ru.ailabs.kontinuous.controller.ControllerDispatcher
import java.util.HashSet
import java.util.Properties
import ru.ailabs.kontinuous.annotation.AnnotationScanner
import ru.ailabs.kontinuous.logger.LoggerFactory
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import ru.ailabs.kontinuous.configuration.Configuration
import ru.ailabs.kontinuous.configuration.configuration
import ru.ailabs.kontinuous.persistance.HibernateSession

//import ru.ailabs.kontinuous.configuration.Configuration
//import ru.ailabs.kontinuous.configuration

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 03.02.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */

open class Application() {

    class object {
        var instance : Application? = null
        fun create() : Application {
            val classes = Reflections("").getSubTypesOf(javaClass<Application>())
            val app = classes?.first() //as Class<Application>
            if(app == null)
                instance = app!!.newInstance()
            else
                instance = Application()
            return instance!!
        }
    }

    val logger = LoggerFactory.getLogger("Kontinuous.Application");

    val conf = configure()
    val dispatcher = ControllerDispatcher(conf.routes)
    val properties = Properties();

    {
        val stream = javaClass.getClassLoader()!!.getResourceAsStream("config/application.properties")
        if (stream != null) {
            properties.load(stream)
        }
        for (initializer in conf.initializers) {
            initializer()
        }
    }

    open fun configure(init: Configuration.() -> Unit = {}) : Configuration  = configuration {
        initialize {
            HibernateSession.init(this)
        }
        init()
    }

    fun getProperty(val name: String, val default: String? = null) : String? {
        return properties.getProperty(name, default)
    }

}