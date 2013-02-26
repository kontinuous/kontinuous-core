package ru.ailabs.kontinuous.tests.krypto

import org.junit.Test
import ru.ailabs.kontinuous.krypto.Kodecs
import kotlin.test.assertEquals

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 25.02.13
 * Time: 13:42
 */
class KodecsTest {

    Test fun testToHex() {
        val result = Kodecs.toHexString("abcdefghijklmnopqrstuvwxyz1234567890".getBytes())
        assertEquals("6162636465666768696a6b6c6d6e6f707172737475767778797a31323334353637383930", result)
    }
}
