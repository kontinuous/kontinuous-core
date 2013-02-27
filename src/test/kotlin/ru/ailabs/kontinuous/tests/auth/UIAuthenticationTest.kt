/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 27.01.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */

package ru.ailabs.kontinuous.tests.auth

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import ru.ailabs.kontinuous.NettyServer
import ru.ailabs.kontinuous.auth.authenticate
import ru.ailabs.kontinuous.auth.authenticated
import ru.ailabs.kontinuous.auth.getUserId
import ru.ailabs.kontinuous.controller.Action
import ru.ailabs.kontinuous.controller.Cookie
import ru.ailabs.kontinuous.controller.Ok
import ru.ailabs.kontinuous.controller.Redirect
import ru.ailabs.kontinuous.controller.helper.asMap
import ru.ailabs.kontinuous.controller.helper.render
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.initializer.ApplicationDiscovery
import kotlin.test.assertTrue

object UIController {
    val secured = Action ({
        Ok(render("views/auth/index.tmpl.html", hashMapOf("name" to "Test passed!!!")))
    })

    val loginPost = Action ({ ctx ->

        println("Header cookies: " + ctx.requestHeaders.cookies.keySet().fold("", {s, m -> s + ";" + m}))

        val form = ctx.body.asMap()

        if (form["user"].equals("user")
        && form["pass"].equals("password")) {
            ctx.userSession.authenticate("1")
            println("USer ID: ${ctx.userSession.getUserId()}")
            println(form["redirect"])
            Redirect(form["redirect"]!!)//.withCookies(Cookie("id", "id"))
        } else
            Redirect(ctx.namedParameters["url"]!!)
    })

    val loginGet = Action ({ ctx ->
        Ok(render("views/auth/login.tmpl.html", ctx.namedParameters)).withCookies(Cookie("id", "id"))
    })
    val action = Action ({ ctx ->
        Ok(render("views/auth/login.tmpl.html", ctx.namedParameters))
    })
    val redirect = Action ({ ctx ->
        Redirect("/action").withCookies(Cookie("id2", "id"))
    })
}

class UITestApplication: Application() {
    {
        add {
            authenticated(UIController.loginGet) {
                get("/secured", UIController.secured)
            }
            authenticated("/login") {
                get("/redirect", UIController.secured)
            }
            get("/action", UIController.action)
            get("/redirect2", UIController.redirect)
            get("/login", UIController.loginGet)
            post("/login", UIController.loginPost)
        }
        initialize()
        properties.put("application.secret", "1234")
    }
}

object Server {
    val driver = HtmlUnitDriver()
    val server = NettyServer(object : ApplicationDiscovery {
        override fun find(): Class<out Application> = javaClass<UITestApplication>()

    });
    {
        server.start()
    }
}

class UIAuthenticationTest {

    val server = Server.server
    val driver = Server.driver

    Test fun shouldRedirectToLoginPage() {
        driver.get("http://localhost:8080/redirect")
        //        println(driver.getPageSource())
        assertEquals("http://localhost:8080/login", driver.getCurrentUrl())
    }

    Test fun shouldNotRedirectToLoginPage() {
        driver.get("http://localhost:8080/secured")
        //        println(driver.getPageSource())
        assertEquals("http://localhost:8080/secured", driver.getCurrentUrl())
    }

    Test fun shouldHaveCookie() {
        driver.get("http://localhost:8080/login")
        println("Cookies: " + driver.manage()?.getCookies()?.fold("", {s, c -> s + ";" + c.getName()}))
        assertNotNull(driver.manage()?.getCookieNamed("id"))
        driver.get("http://localhost:8080/redirect2")
        println("Cookies: " + driver.manage()?.getCookies()?.fold("", {s, c -> s + ";" + c.getName()}))
        assertNotNull(driver.manage()?.getCookieNamed("id2"))
    }

    Test fun shouldBeLoggedIn() {
        driver.get("http://localhost:8080/secured")
        val elemName = driver.findElement(By.name("user"))
        elemName?.sendKeys("user")
        val elemPass = driver.findElement(By.name("pass"))
        elemPass?.sendKeys("password")
        val elemSubmit = driver.findElement(By.name("submit"))
        elemSubmit?.submit()
        println("Cookies: " + driver.manage()?.getCookies()?.fold("", {s, c -> s + ";" + c.getName()}))
        assertNotNull(driver.manage()?.getCookieNamed("_kontinuous_session"))
        assertEquals("http://localhost:8080/secured", driver.getCurrentUrl())
        assertTrue(driver.getPageSource()!!.contains("Hello Test passed!!!!"))
    }
}