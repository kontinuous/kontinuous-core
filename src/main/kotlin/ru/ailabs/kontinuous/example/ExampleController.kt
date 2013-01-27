/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.example

import ru.ailabs.kontinuous.annotation.path
import ru.ailabs.kontinuous.annotation.routes
import ru.ailabs.kontinuous.controller.Action

object Controller {
    val index =  Action ({
        Pair(hashMapOf("name" to "index"), "index.vm")
    })

    val post = Action ({
        Pair(hashMapOf("name" to "post"), "index.vm")
    })
}

routes class Routes {

    path("/")  val index = Controller.index
    path("/post")  val post = Controller.post
}
