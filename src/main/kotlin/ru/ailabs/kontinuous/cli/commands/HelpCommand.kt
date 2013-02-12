package ru.ailabs.kontinuous.commands

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 10.02.13
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
class HelpCommand() : Command {

    override fun execute(val command: String?) : Boolean {
        if (command.equals("help")) {
            println("Welcome to kontinuous framework!\n"
            +"\n"
            +"start              Start the kontinuous\n"
            +"stop               Stop the kontnuous\n"
            +"help               Display help information\n"
            +"exit               To exit program");
        }
        return true
    }
}