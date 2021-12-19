package top.dzhh;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.netty.handler.CommandDecoder;
import top.dzhh.netty.handler.CommandHandler;
import top.dzhh.netty.handler.ResponseEncoder;
import top.dzhh.redis.core.RedisCore;
import top.dzhh.redis.core.RedisCoreImpl;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
@Slf4j
public class RedisServer {
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private final RedisCore redisCore = new RedisCoreImpl();

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
                .handler(new LoggingHandler(LogLevel.INFO))
                .localAddress("localhost", 6379)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        ChannelPipeline channelPipeline = nioSocketChannel.pipeline();
                        channelPipeline.addLast(new ResponseEncoder());
                        channelPipeline.addLast(new CommandDecoder());
                        channelPipeline.addLast(new CommandHandler(redisCore));
                    }
                });
        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            log.error("redis mock server 启动成功：" + sync.channel().localAddress().toString());
        } catch (InterruptedException e) {
            //
            log.warn("Interrupted!", e);
            throw new RuntimeException(e);
        }
    }
}
