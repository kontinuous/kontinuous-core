package ru.ailabs.kontinuous.controller

import ru.ailabs.kontinuous.view.ViewResolver

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 02.02.13
 * Time: 23:31
 */
object ControllerHelper {
    val viewResolver = ViewResolver()

    fun render(viewName: String, params: Map<String, Any>): String {
        return viewResolver.resolveView(params, viewName)
    }
}
