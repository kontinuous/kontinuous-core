package ru.ailabs.kontinuous.controller

import ru.ailabs.kontinuous.controller.helper.render
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 02.02.13
 * Time: 17:23
 */
class Action500(val e: Exception) : Action({ context ->
    val params = context.namedParameters.toLinkedMap()
    val output = ByteArrayOutputStream()
    e.printStackTrace(PrintStream(output, true))
    params.put("exception.message", e.getMessage()!!);
    params.put("exception.trace", output.toString());
    ServerError(render("views/500.tmpl.html", params))
})

object Action404 : Action({ context ->
    NotFound(render("views/404.tmpl.html", context.namedParameters))
})

object TODO : Action({ context ->
    Ok(render("views/todo.tmpl.html"))
})