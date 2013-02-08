package ru.ailabs.kontinuous.controller

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 02.02.13
 * Time: 23:34
 */
class ResponseHeader(val status: Int, val headers: MutableMap<String, String> = hashMapOf()) {

    fun toString(): String {
        return "${status}, ${headers}"
    }
}

trait WithHeaders {
    fun withHeaders(headers: MutableMap<String, String>)
}

trait HttpResult {

}

fun emptyBody(): String {
    return ""
}

open class SimpleResult(val header: ResponseHeader, val body: String) : HttpResult, WithHeaders {
    override fun withHeaders(headers: MutableMap<String, String>) {
        header.headers.putAll(headers)
    }
}

open class Status(val status: Int) : SimpleResult(header = ResponseHeader(HttpStatus.OK), body = emptyBody())

class Ok(body: String) : SimpleResult(header = ResponseHeader(HttpStatus.OK), body = body)
class NotFound(body: String = emptyBody()) : SimpleResult(header = ResponseHeader(status = HttpStatus.NOT_FOUND),
                                                          body = body)
class ServerError(body: String = emptyBody()) : SimpleResult(header = ResponseHeader(status = HttpStatus.INTERNAL_SERVER_ERROR),
        body = body)
class Redirect(url: String) : SimpleResult(header = ResponseHeader(status = HttpStatus.SEE_OTHER,
                                                                   headers = hashMapOf(HttpHeaderNames.LOCATION to url)),
                                           body = emptyBody())