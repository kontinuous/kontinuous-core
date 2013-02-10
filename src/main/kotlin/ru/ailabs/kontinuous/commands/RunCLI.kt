package ru.ailabs.kontinuous.commands

import java.io.InputStreamReader
import java.io.BufferedReader
import sun.security.action.GetBooleanAction

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 07.02.13
 * Time: 0:01
 * To change this template use File | Settings | File Templates.
 */
fun main(args: Array<String>) {

    var br : BufferedReader = BufferedReader(InputStreamReader(System.`in`))
    var exitKey : Boolean = true

    while (exitKey) {
        var str: String?  = br.readLine()
        if (str.equals("exit")){
            exitKey = false
        }
        var start : Command = StartCommand(str)
        var stop : Command = StopCommand(str)
        start.execute()
        stop.execute()
    }
}
