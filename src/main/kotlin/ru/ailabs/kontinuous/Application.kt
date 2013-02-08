package ru.ailabs.kontinuous.initializer

import org.reflections.Reflections
import ru.ailabs.kontinuous.annotation.initializers
import ru.ailabs.kontinuous.controller.ControllerDispatcher
import java.util.HashSet
import java.util.Properties

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 03.02.13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */

class Application {

    val dispatcher = ControllerDispatcher()
    val properties = Properties();

    {
        val stream = javaClass.getClassLoader()!!.getResourceAsStream("config/application.properties")
        if (stream != null)
            properties.load(stream);

        fun scanForInitializers(): jet.MutableSet<java.lang.Class<out jet.Any?>>? {
            return Reflections("").getTypesAnnotatedWith(javaClass<initializers>())
        }

        for (cls in scanForInitializers()!!.toCollection()) {
            val initializers = cls.newInstance() as InitializersBase
            initializers.init(this)
        }
    }

    fun getProperty(val name: String, val default: String? = null) : String? {
        return properties.getProperty(name, default)
    }

}