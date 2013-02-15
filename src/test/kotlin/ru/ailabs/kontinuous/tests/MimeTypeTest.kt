package ru.ailabs.kontinuous.tests

import org.junit.Test
import javax.activation.MimeType
import ru.ailabs.kontinuous.controller.MimeTypes
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 14.02.13
 * Time: 10:57
 */
class MimeTypesTest {
    Test fun testTypesBuilder() {
        assertEquals("text/html", MimeTypes.defaultTypes["html"])
    }

    Test fun testWithFileName() {
        assertEquals("text/html", MimeTypes.forFileName("/some/path/to/index.html"))
    }

    Test fun testWithFileNameWithoutExt() {
        assertNull(MimeTypes.forFileName("/some/path/to/index"))
    }

    Test fun testWithExt() {
        assertEquals("text/html", MimeTypes.forExtension("html"))
    }

    Test fun testWithWrongFileType() {
        assertNull(MimeTypes.forExtension("strange_file_ext"))
    }
}