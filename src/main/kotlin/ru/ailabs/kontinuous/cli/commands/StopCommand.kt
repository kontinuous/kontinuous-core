package ru.ailabs.kontinuous.commands

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 06.02.13
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */
class StopCommand() : Command {

    override fun execute(val command: String?) : Boolean {
        if (command.equals("stop"))
            println("Stop Kontinuous")
        return true
    }
}