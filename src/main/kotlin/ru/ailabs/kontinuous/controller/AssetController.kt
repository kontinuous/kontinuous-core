package ru.ailabs.kontinuous.controller

import java.io.File
import java.io.RandomAccessFile
import org.jboss.netty.channel.DefaultFileRegion

/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 14.02.13
 * Time: 9:20
 */
object AssetController {

    val PATH_FOR_ASSETS = "public"

    val at = Action { context ->
        val fileName = context.namedParameters["file"]

        if (fileName != null) {
            val resourceName = PATH_FOR_ASSETS + if(fileName.startsWith("/")) fileName else "/" + fileName

            if (!File(resourceName).exists() || File(resourceName).isDirectory() || !File(resourceName).getCanonicalPath().startsWith(File(PATH_FOR_ASSETS).getCanonicalPath())) {
                Action404.handler(context)
            } else {
                val raf = RandomAccessFile(resourceName, "r")
                val fileLength = raf.length()

                var mimeType = MimeTypes.forFileName(fileName)
                if(mimeType == null) {
                    mimeType = MimeTypes.BINARY
                }

                val response = SimpleResult(header = ResponseHeader(status = HttpStatus.OK,
                                                                    headers = hashMapOf(
                                                                            HttpHeaderNames.CONTENT_LENGTH to fileLength.toString(),
                                                                            HttpHeaderNames.CONTENT_TYPE to mimeType.toString() + "; charset=utf-8",
                                                                            HttpHeaderNames.VARY to HttpHeaderNames.ACCEPT_ENCODING
                                                                    )),
                                           body = DefaultFileRegion(raf.getChannel(), 0, fileLength))
                response
            }
        } else {
            Action404.handler(context)
        }
    }
}
