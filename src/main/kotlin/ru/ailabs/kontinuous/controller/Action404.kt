package ru.ailabs.kontinuous.controller

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 02.02.13
 * Time: 17:23
 */
object Action404 : Action({ context ->
    Pair(context.namedParameters, "views/404.tmpl.html")
})