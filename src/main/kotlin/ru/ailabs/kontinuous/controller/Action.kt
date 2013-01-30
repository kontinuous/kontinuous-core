package ru.ailabs.kontinuous.controller

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 4:15 PM
 */
// TODO: extract result to new type
public open class Action(private val handler: () -> Pair<Map<String, Any>, String>) {
    fun handle(context : String) : Pair<Map<String, Any>, String> {
        return handler()
    }
}
