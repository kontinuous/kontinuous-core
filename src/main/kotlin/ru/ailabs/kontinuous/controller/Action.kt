package ru.ailabs.kontinuous.controller

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 1/27/13
 * Time: 4:15 PM
 */
public open class Action(public open val handler: (Context) -> SimpleResult) {
}

class ActionHandler(val action: Action, val namedParams: Map<String, String>)
