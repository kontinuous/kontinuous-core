package ru.ailabs.kontinuous.initializer

import java.util.HashSet

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 04.02.13
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */

trait InitializersBase {
    fun init(val app: Application)
}
