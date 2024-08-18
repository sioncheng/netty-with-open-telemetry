package app;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NettyServer {

    @Resource
    private HttpRequestHandler handler;

    public void start() throws Exception {
        EventLoopGroup eventLoop = new NioEventLoopGroup();
        EventExecutor handlerExecutor = new UnorderedThreadPoolEventExecutor(2);
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(eventLoop)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new HttpServerCodec());// http 编解码
                        pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器                                                                     512*1024为接收的最大contentlength
                        pipeline.addLast(handlerExecutor, handler);// 请求处理器
                    }
                }).bind("0.0.0.0", 8080).sync();
    }
}
