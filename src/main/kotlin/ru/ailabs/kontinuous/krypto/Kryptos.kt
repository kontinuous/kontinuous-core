package ru.ailabs.kontinuous.krypto

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import ru.ailabs.kontinuous.initializer.Application
import ru.ailabs.kontinuous.KontinuousException

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 25.02.13
 * Time: 13:23
 */
object Kryptos {

    val secret: String? =  Application.instance?.getProperty("application.secret")

    /**
     * Signs the given String with HMAC-SHA1 using the given key.
     */
    fun sign(message: String, key: ByteArray): String {
        val mac = Mac.getInstance("HmacSHA1")!!
        mac.init(SecretKeySpec(key, "HmacSHA1"))
        return Kodecs.toHexString(mac.doFinal(message.getBytes("utf-8"))!!)
    }

    /**
     * Signs the given String with HMAC-SHA1 using the application’s secret key.
     */
    fun sign(message: String): String {
        if(secret != null) {
            return sign(message, secret.getBytes("utf-8"))
        } else {
            throw KontinuousException("Missing application.secret")
        }
    }
}
