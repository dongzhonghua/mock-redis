package top.dzhh;

import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
public class RedisServer {
    private static final Logger LOGGER = Logger.getLogger(RedisServer.class);
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    public RedisServer() {
    }


    public static void main(String[] args) {
        new RedisServer().start();
    }

    public void start() {

        start0();
    }

    public void start0() {
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress("localhost", 6379)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline channelPipeline = nioSocketChannel.pipeline();
                        channelPipeline.addLast(new ResponseEncoder());
                        channelPipeline.addLast(new CommandDecoder());
                        channelPipeline.addLast(new CommandHandler());
                    }
                });
        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            LOGGER.info(sync.channel().localAddress().toString());
        } catch (InterruptedException e) {
            //
            LOGGER.warn("Interrupted!", e);
            throw new RuntimeException(e);
        }
    }
}
