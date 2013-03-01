package ru.ailabs.kontinuous.commands

/**
 * Created with IntelliJ IDEA.
 * User: Никита
 * Date: 10.02.13
 * Time: 19:39
 */
class HelpCommand() : Command {

    override fun execute() : Boolean {
        println("""Welcome to kontinuous framework!

start              Start the kontinuous
stop               Stop the kontnuous
help               Display help information
exit               To exit program""");
        return true
    }
}