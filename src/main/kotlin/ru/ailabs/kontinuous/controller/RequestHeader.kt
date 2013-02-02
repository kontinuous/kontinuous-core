package ru.ailabs.kontinuous.controller

import org.jboss.netty.handler.codec.http.HttpVersion
import org.jboss.netty.handler.codec.http.HttpMethod

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 02.02.13
 * Time: 16:24
 */
class RequestHeader(val keepAlive: Boolean,
                    val requestProtocolVersion: HttpVersion,
                    val uri: String,
                    val path: String,
                    val method: HttpMethod,
                    val parameters: Map<String, List<String>>,
                    val headers: List<Map<String, String>>) {
}
