package ru.ailabs.kontinuous.commands

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 06.02.13
 * Time: 23:56
 */
class StopCommand() : Command {

    override fun execute() : Boolean {
        println("Stop Kontinuous")
        return true
    }
}