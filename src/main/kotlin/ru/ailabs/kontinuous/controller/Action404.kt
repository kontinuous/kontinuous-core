package ru.ailabs.kontinuous.controller

import ru.ailabs.kontinuous.controller.helper.render
/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 02.02.13
 * Time: 17:23
 */
object Action404 : Action({ context ->
    NotFound(render("views/404.tmpl.html"))
})