package example.controllers

import ru.ailabs.kontinuous.annotations.controller
import ru.ailabs.kontinuous.controller.Action

object HelloController {
    val index = Action ({
        Pair(hashMapOf("name" to "Alex Khamutov"), "indexView")
    })
}
