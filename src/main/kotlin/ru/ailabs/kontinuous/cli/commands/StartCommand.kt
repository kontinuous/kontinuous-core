package ru.ailabs.kontinuous.commands

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 06.02.13
 * Time: 23:43
 */

class StartCommand() : Command {

    override fun execute() : Boolean {
        println("Start Kontinuous")
        return true
    }
}