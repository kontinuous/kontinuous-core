package ru.ailabs.kontinuous.controller

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 4:15 PM
 */
// TODO: extract result to new type
public open class Action(private val handler: (Context) -> Pair<Map<String, *>, String>) {
    fun handle(context : Context) : Pair<Map<String, *>, String> {
        return handler(context)
    }
}
