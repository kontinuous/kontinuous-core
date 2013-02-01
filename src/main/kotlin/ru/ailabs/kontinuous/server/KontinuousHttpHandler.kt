package ru.ailabs.kontinuous.server

import org.jboss.netty.channel.SimpleChannelUpstreamHandler
import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.handler.codec.http.HttpRequest
import ru.ailabs.kontinuous.logger.LoggerFactory
import org.jboss.netty.handler.codec.http.QueryStringDecoder

import org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive
import org.jboss.netty.handler.codec.http.HttpResponse
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.handler.codec.http.HttpResponseStatus.OK
import org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE
import org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH
import org.jboss.netty.handler.codec.http.HttpHeaders.Names.COOKIE
import org.jboss.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE
import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.util.CharsetUtil
import org.jboss.netty.handler.codec.http.CookieDecoder
import org.jboss.netty.handler.codec.http.Cookie
import java.util.Set
import org.jboss.netty.handler.codec.http.CookieEncoder
import org.jboss.netty.channel.ChannelFuture
import org.jboss.netty.channel.ChannelFutureListener

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 01.02.13
 * Time: 22:49
 */
class KontinuousHttpHandler : SimpleChannelUpstreamHandler() {

    val logger = LoggerFactory.getLogger("ru.ailabs.kontinuous.server.KontinuousHttpHandler")

    public override fun messageReceived(ctx: ChannelHandlerContext?, e: MessageEvent?) {
        val nettyHttpRequest = e?.getMessage()
        when(nettyHttpRequest) {
            is HttpRequest -> {
                logger.debug("Http request received by netty: ${nettyHttpRequest}");

                val keepAlive = isKeepAlive(nettyHttpRequest)
                val requestProtocolVersion = nettyHttpRequest.getProtocolVersion()
                val requestUri = QueryStringDecoder(nettyHttpRequest.getUri())

                val requestParams = requestUri.getParameters()

                val headers = nettyHttpRequest.getHeaders()

                writeResponse(e!!, nettyHttpRequest, "keepAlive: ${keepAlive} requestProtocolVersion: ${requestProtocolVersion} requestUri: ${requestUri} requestParams: ${requestParams}")
            }
            else -> {

            }
        }
    }

    private fun writeResponse(e: MessageEvent, request: HttpRequest, buf: String) {
         // Decide whether to close the connection or not.
         val keepAlive = isKeepAlive(request);

         // Build the response object.
         val response = DefaultHttpResponse(HTTP_1_1, OK);
         response.setContent(ChannelBuffers.copiedBuffer(buf, CharsetUtil.UTF_8));
         response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");

         if (keepAlive) {
             // Add 'Content-Length' header only for a keep-alive connection.
             response.setHeader(CONTENT_LENGTH, response.getContent()!!.readableBytes());
         }

         // Encode the cookie.
         val cookieString = request.getHeader(COOKIE);
         if (cookieString != null) {
                 val cookieDecoder = CookieDecoder();
                 val cookies = cookieDecoder.decode(cookieString)!!;
                 if(!cookies.isEmpty()) {
                     // Reset the cookies if necessary.
                     val cookieEncoder = CookieEncoder(true);
                     for (cookie in cookies) {
                         cookieEncoder.addCookie(cookie);
                     }
                 response.addHeader(SET_COOKIE, cookieEncoder.encode());
             }
         }

         // Write the response.
         val future = e.getChannel()?.write(response);

         // Close the non-keep-alive connection after the write operation is done.
         if (!keepAlive) {
             future?.addListener(ChannelFutureListener.CLOSE);
         }
     }
}