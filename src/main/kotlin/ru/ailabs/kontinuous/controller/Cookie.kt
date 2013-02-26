package ru.ailabs.kontinuous.controller

import org.jboss.netty.handler.codec.http.CookieDecoder
import org.jboss.netty.handler.codec.http.CookieEncoder
import org.jboss.netty.handler.codec.http.DefaultCookie
import ru.ailabs.kontinuous.krypto.Kryptos
import kotlin.nullable.filterNot

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 21.02.13
 * Time: 16:09
 */
class Cookie(val name: String, val value: String, val maxAge: Int = -1, val path: String = "/", val domain: String? = null, val secure: Boolean = false, val httpOnly: Boolean = true)

object Cookies {

    val userId = "USER_ID"
    val sessionId = "SESSION_ID"

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

open class CookieFolding {

    var isSigned = true

    /**
     * Encodes the data as a `String`.
     */
    fun encode(data: Map<String, String>): String {
        val encoded: (data: Map<String, String>) -> String = { data ->
            val filtered = hashMapOf<String, String>()
            for ((key, value) in data) {
                if(!key.contains(":")) {
                    filtered.put(key, value)
                }
            }
            java.net.URLEncoder.encode(filtered.map { elem -> elem.key + ":" + elem.value }.makeString("\u0000"), "UTF-8")
        }

        val encodedString = encoded(data)
        if (isSigned) {
            return Kryptos.sign(encodedString) + "-" + encodedString
        } else {
            return encodedString
        }
    }

    /**
     * Decodes from an encoded `String`.
     */
    fun decode(data: String): Map<String, String> {

        val urldecode: (data: String) -> Map<String, String> = { data ->
            java.net.URLDecoder.decode(data, "UTF-8")
                    .split("\u0000")
                    .map { value -> value.split(":")}
                    .fold(hashMapOf<String,String>()) { map, elem ->
                        if(!elem[0].isEmpty()) {
                            map.put(elem[0], elem.drop(1).makeString(":"));
                        }
                        map
                    }
        }

        val safeEquals: (a: String, b: String) -> Boolean = { a, b ->
            if (a.length != b.length) {
                false
            } else {
                var equal = 0
                for (i in 0..a.size-1) {
                    a.charAt(i).toInt()
                    equal = equal or (a[i].toInt() xor b[i].toInt())
                }
                equal == 0
            }
        }

        try {
            if (isSigned) {
                val splitted = data.split("-")
                val message = splitted.toList().tail.makeString("-")
                if (safeEquals(splitted[0], Kryptos.sign(message))) {
                    return urldecode(message)
                } else {
                    return hashMapOf<String, String>()
                }
            } else {
                return urldecode(data)
            }
        } catch(e: Exception) {
            return hashMapOf<String, String>()
        }
    }
}
