package ru.ailabs.kontinuous.commands

import java.io.InputStreamReader
import java.io.BufferedReader
import sun.security.action.GetBooleanAction
import java.util.ArrayList
import java.util.HashMap

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 07.02.13
 * Time: 0:01
 * To change this template use File | Settings | File Templates.
 */
var exitKey: Boolean = true

fun main(args: Array<String>) {

    var br : BufferedReader = BufferedReader(InputStreamReader(System.`in`))
    var pref: String = "konti$>"
    var start : Command = StartCommand()
    var stop : Command = StopCommand()
    var help : Command = HelpCommand()
    var exit : Command = ExitCommand()

    val commandMap = HashMap<String, Command>()

    commandMap.put("start",start)
    commandMap.put("stop",stop)
    commandMap.put("help",help)
    commandMap.put("exit", exit)

    while (exitKey) {
        print(pref)
        var str : String? = br.readLine()
        if (str!!.length()>0){
            if (str in commandMap.keySet()){
                var success : Boolean = commandMap.get(str)!!.execute()
                if (success){
                    continue
                } else {
                    println("Command run error!")
                }
            } else {
                println("Unknown command!")
            }
        }
    }
}
