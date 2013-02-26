package ru.ailabs.kontinuous.krypto

import java.util.Arrays

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 25.02.13
 * Time: 13:26
 */
object Kodecs {

    private val hexChars = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * Converts a byte array into an array of characters that denotes a hexadecimal representation.
     */
    fun toHex(array: ByteArray): CharArray {
        val result = CharArray(array.size * 2)
        for (i in 0..array.size-1) {
            val b = array[i].toInt() and 0xff
            result[2 * i] = hexChars[b shr 4]
            result[2 * i + 1] = hexChars[b and 0xf]
        }
        return result
    }

    /**
     * Converts a byte array into a `String` that denotes a hexadecimal representation.
     */
    fun toHexString(array: ByteArray): String {
        return String(toHex(array))
    }
}
