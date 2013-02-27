package ru.ailabs.kontinuous.controller.helper

import ru.ailabs.kontinuous.view.ViewResolver
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URLEncoder
import java.net.URI
import java.net.URLDecoder

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 02.02.13
 * Time: 23:31
 */
object ControllerHelper {
    val viewResolver = ViewResolver()
}

fun render(viewName: String, params: Map<String, Any> = hashMapOf()): String {
    return ControllerHelper.viewResolver.resolveView(params, viewName)
}

fun render_json(params: Any): String {
    val mapper = ObjectMapper()
    return mapper.writeValueAsString(params)!!
}

public fun ByteArray.asMap() : Map<String, String> =
    String(this).split('&').fold(hashMapOf<String, String>(), {map, it ->
        val arr = it.split('=') map { URLDecoder.decode(it, "UTF-8") }
        map[arr.get(0)] = arr.get(1)
        map
    })


public fun ByteArray.asJson(clazz: Class<Any>) : Any? {
    val mapper = ObjectMapper()
    return mapper.readValue(this, clazz)
}
