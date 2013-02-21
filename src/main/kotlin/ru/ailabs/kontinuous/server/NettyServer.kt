package ru.ailabs.kontinuous

import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import java.util.concurrent.Executors
import org.jboss.netty.channel.ChannelPipelineFactory
import org.jboss.netty.channel.ChannelPipeline
import org.jboss.netty.channel.Channels

import org.jboss.netty.handler.codec.http.HttpRequestDecoder
import org.jboss.netty.handler.codec.http.HttpResponseEncoder
import org.jboss.netty.handler.codec.http.HttpContentCompressor

import java.net.InetSocketAddress

import ru.ailabs.kontinuous.server.KontinuousHttpHandler
import ru.ailabs.kontinuous.logger.LoggerFactory
import ru.ailabs.kontinuous.initializer.Application


/**
 * Alien Invaders Ltd.
 * User: Aleksandr Khamutov
 * Date: 01.02.13
 * Time: 22:14
 */
// http://docs.jboss.org/netty/3.2/xref/org/jboss/netty/example/http/snoop/HttpServer.html

class HttpServerPipelineFactory : ChannelPipelineFactory {

    public override fun getPipeline(): ChannelPipeline? {
        // Create a default pipeline implementation.
        val pipeline = Channels.pipeline()!!;

        // Uncomment the following line if you want HTTPS
        //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
        //engine.setUseClientMode(false);
        //pipeline.addLast("ssl", new SslHandler(engine));

        pipeline.addLast("decoder", HttpRequestDecoder());
        // Uncomment the following line if you don't want to handle HttpChunks.
        //pipeline.addLast("aggregator", new HttpChunkAggregator(1048576));
        pipeline.addLast("encoder", HttpResponseEncoder());
        // Remove the following line if you don't want automatic content compression.
        //pipeline.addLast("deflater", HttpContentCompressor());
        pipeline.addLast("handler", KontinuousHttpHandler());
        return pipeline;
    }

}

class NettyServer {

    val logger = LoggerFactory.getLogger("Netty")

    var application: Application? = null

    private val serverBootstrap = ServerBootstrap(
        NioServerSocketChannelFactory(
            Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool()
        )
    )

    fun start() {
        val port = 8080;

        serverBootstrap.setPipelineFactory(HttpServerPipelineFactory());

        serverBootstrap.bind(InetSocketAddress(port));

        application = Application.create()

        logger.info("Listening for HTTP on ${port}")
    }
}
