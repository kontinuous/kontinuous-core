package ru.ailabs.kontinuous.tests.krypto

import org.junit.Test
import ru.ailabs.kontinuous.controller.CookieFolding
import ru.ailabs.kontinuous.initializer.Application
import kotlin.test.assertNotNull
import ru.ailabs.kontinuous.initializer.ApplicationDiscovery
import kotlin.test.assertEquals
import ru.ailabs.kontinuous.configuration.Configuration
import ru.ailabs.kontinuous.configuration.configuration

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 25.02.13
 * Time: 17:26
 */
class TestApplication : Application() {
    protected override fun configure(): Configuration = configuration {
    }
}

public class CookieDecoderTest() {
    Test fun testFolding() {
        val folder = CookieFolding()
        Application.create(object : ApplicationDiscovery {
            override fun find(): Class<out Application> {
                return javaClass<TestApplication>()
            }
        })
        assertNotNull(Application.instance)
        Application.instance?.properties?.put("application.secret", "1234")
        val initialObject = hashMapOf("url" to "www.ailabs.ru", "username" to "alex khamutov")
        val folded = folder.encode(initialObject)
        val decodedObject = folder.decode(folded)
        assertEquals(initialObject, decodedObject)
    }

    Test fun testFoldingEmpty() {
        val folder = CookieFolding()
        Application.create(object : ApplicationDiscovery {
            override fun find(): Class<out Application> {
                return javaClass<TestApplication>()
            }
        })
        assertNotNull(Application.instance)
        Application.instance?.properties?.put("application.secret", "1234")
        val initialObject = hashMapOf<String, String>()
        val folded = folder.encode(initialObject)
        val decodedObject = folder.decode(folded)
        assertEquals(initialObject, decodedObject)
    }

    Test fun testUnfoldingCorrupted() {
        val folder = CookieFolding()
        Application.create(object : ApplicationDiscovery {
            override fun find(): Class<out Application> {
                return javaClass<TestApplication>()
            }
        })
        assertNotNull(Application.instance)
        Application.instance?.properties?.put("application.secret", "1234")
        val initialObject = hashMapOf("url" to "www.ailabs.ru", "username" to "alex khamutov")
        val folded = "01234${folder.encode(initialObject)}"
        val decodedObject = folder.decode(folded)
        assertEquals(hashMapOf<String, String>(), decodedObject)
    }
}

