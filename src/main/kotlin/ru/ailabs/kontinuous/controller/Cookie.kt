package ru.ailabs.kontinuous.controller

import org.jboss.netty.handler.codec.http.CookieDecoder
import org.jboss.netty.handler.codec.http.CookieEncoder
import org.jboss.netty.handler.codec.http.DefaultCookie

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 21.02.13
 * Time: 16:09
 */
class Cookie(val name: String, val value: String, val maxAge: Int = -1, val path: String = "/", val domain: String? = null, val secure: Boolean = false, val httpOnly: Boolean = true)

object Cookies {

    fun encode(cookies: Set<Cookie>): String {
        val encoder = CookieEncoder(true)
        val newCookies = cookies.map { c ->
            encoder.addCookie({
                val nc = DefaultCookie(c.name, c.value)
                nc.setMaxAge(c.maxAge)
                nc.setPath(c.path)
                if(c.domain != null) {
                    nc.setDomain(c.domain)
                }
                nc.setSecure(c.secure)
                nc.setHttpOnly(c.httpOnly)
                nc
            }())
            encoder.encode()
        }
        return newCookies.makeString("; ")
    }

    fun decode(cookieHeader: String): Set<Cookie> {
        return CookieDecoder().decode(cookieHeader)!!.fold(hashSetOf<Cookie>()) { cookieSet, cookie ->
            cookieSet.add(Cookie(cookie.getName()!!, cookie.getValue()!!))
            cookieSet
        }
    }

    fun merge(cookieHeader: String, cookies: Set<Cookie>): String {
        return encode((cookies.toList() + decode(cookieHeader).toList()).toSet())
    }
}
