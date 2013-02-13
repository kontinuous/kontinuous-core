package ru.ailabs.kontinuous.commands

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 10.02.13
 * Time: 19:39
 */
class ExitCommand() : Command {

    override fun execute() : Boolean {
        println("Exit Kontinuous")
        exitKey = false
        return true
    }
}