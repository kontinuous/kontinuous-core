package ru.ailabs.kontinuous.configuration

import java.util.HashMap
import java.util.HashSet
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.UrlMatcher
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.persistance.HibernateSession
import ru.ailabs.kontinuous.logger.LoggerFactory

//
///**
// * User: andrew
// * Date: 19.02.13
// * Time: 14:52
// */
//
//public trait Factory<T: Initializer> {
//    public fun create() : T
//}

fun configuration(init: Configuration.() -> Unit): Configuration {
    val config = Configuration()
    config.init()
    return config
}

class Configuration {

    private val logger = LoggerFactory.getLogger(javaClass<Configuration>())

    val routes = HashMap<String, List<Method>>()
    val initializers = HashSet<Application.() -> Unit>()

    fun get(path: String, action: Action) {
        val route = Get(path, action)
        routes.put(route.method, routes.getOrPut(route.method, { listOf<Method>() }) + route)
    }

    fun post(path: String, action: Action) {
        val route = Post(path, action)
        routes.put(route.method, routes.getOrPut(route.method, { listOf<Method>() }) + route)
    }

    fun initialize(init: Application.() -> Unit) {
        initializers.add(init)
        logger.debug("Initializers: ${initializers}")
    }

//    fun initialize<T: Initializer>()
//            where class object T : Factory<T> {
//        initializers + {
//            T.create().init(this)
//        }
//    }
}

public abstract class Method(val path: String, val method: String, val action: Action) {
    val matcher = UrlMatcher(path)
}
public class Get(path: String, action: Action): Method(path, "GET", action)
public class Post(path: String, action: Action): Method(path, "POST", action)

//fun project() {
//    configuration {
//        get("/path", Action({Ok("")}))
//        initialize{
//            HibernateSession.init(this)
//        }
//    }
//}
