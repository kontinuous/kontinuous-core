package ru.ailabs.kontinuous.auth

import java.util.UUID
import ru.ailabs.kontinuous.krypto.Kryptos
import org.mindrot.jbcrypt.BCrypt

/**
 * User: andrew
 * Date: 06.03.13
 * Time: 12:49
 */

public abstract class SecuredUser {

    fun password(pass: String) {
        val key = BCrypt.gensalt()!!
        val password = BCrypt.hashpw(pass, key)!!
        passwordAndKey(password, key)
    }

    fun authenticate(pass: String): Boolean {
        if (key() != null){
            val possible = BCrypt.hashpw(pass, key())!!
            return possible.equals(password())
        } else {
            return false
        }
    }

    abstract fun passwordAndKey(password: String, key: String)
    abstract fun key() : String?
    abstract fun password() : String
}