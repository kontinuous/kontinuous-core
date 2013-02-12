package ru.ailabs.kontinuous.commands

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 10.02.13
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
class ExitCommand() : Command {

    override fun execute(val command: String?) : Boolean {
        if (command.equals("exit"))
            println("Exit Kontinuous")
        exitKey = false
        return true
    }
}