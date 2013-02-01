package ru.ailabs.kontinuous.logger

import java.util.logging.Logger
import org.slf4j.Logger

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 01.02.13
 * Time: 18:39
 */
object LoggerFactory {
    fun getLogger(name: String): Logger {
        return org.slf4j.LoggerFactory.getLogger(name)!!
    }
}
