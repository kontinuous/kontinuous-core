package ru.ailabs.kontinuous.controller

import java.util.HashMap

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 25.02.13
 * Time: 18:58
 */
class KontinuousSession(val values: MutableMap<String, String> = hashMapOf()) : CookieFolding() {

    var dirty = false

    fun get(key: String): String? {
        return values.get(key)
    }

    fun isDirty(): Boolean {
        return dirty
    }

    fun set(key: String, value: String) {
        dirty = true
        values.put(key, value)
    }

    fun serialize(): String = encode(values)
    fun deserialize(input: String) {
        dirty = false
        values.clear()
        values.putAll(decode(input))
    }
}