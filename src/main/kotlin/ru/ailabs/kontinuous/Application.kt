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
import ru.ailabs.kontinuous.controller.AssetController
import ru.ailabs.kontinuous.auth.*

//import ru.ailabs.kontinuous.configuration.Configuration
//import ru.ailabs.kontinuous.configuration

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 03.02.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */

trait ApplicationDiscovery {
    fun find(): Class<out Application>
}

open class Application() {

    class object {

        class DefaultApplicationDiscovery : ApplicationDiscovery {

            val logger = LoggerFactory.getLogger("Kontinuous.Application");

            override fun find(): Class<out Application> {
                val classes = Reflections("").getSubTypesOf(javaClass<Application>())
                if(classes == null) {
                    throw RuntimeException("User Application class not found!")
                }
                if(classes.size() != 1) {
                    logger.error("Founded ${classes.size()} User Applications.")
                    logger.error("Default application discoverer expects exactly one User Application class.")
                    logger.error("List of finded User Application classes:")
                    for(applicationClass in classes) {
                        logger.error(applicationClass.getCanonicalName())
                    }
                    logger.error("Please remove unused User Application or provide custom appliction discoverer")
                    throw RuntimeException("Founded ${classes.size()} User Applications.")
                }

                return classes.first()
            }
        }

        var instance: Application? = null

        fun create(discovery: ApplicationDiscovery = DefaultApplicationDiscovery()): Application {
            val app = discovery.find()
            instance = app.newInstance()
            instance?.initialize()
            return instance!!
        }
    }

    val logger = LoggerFactory.getLogger("Kontinuous.Application");

    var conf = configure()

    val dispatcher = ControllerDispatcher()
    public val properties: Properties = Properties();

    fun initialize() {
        val stream = javaClass.getClassLoader()!!.getResourceAsStream("config/application.properties")
        if (stream != null) {
            properties.load(stream)
        }
        for (initializer in conf.initializers) {
            initializer()
        }
        dispatcher.routes = conf.routes
    }

    fun add(init: Configuration.() -> Unit) {
        conf.init()
    }
    fun new(init: Configuration.() -> Unit) {
        conf = configuration { init() }
    }

    open protected fun configure() : Configuration  = configuration {
        initialize {
            HibernateSession.init(this)
        }
//        authenticated("/login") {
            get("/assets/*file", AssetController.at)
//        }
    }

    fun getProperty(val name: String, val default: String? = null) : String? {
        return properties.getProperty(name, default)
    }

}