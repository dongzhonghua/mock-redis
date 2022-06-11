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
import top.dzhh.netty.handler.ClientHandler;
import top.dzhh.netty.handler.CommandDecoder;
import top.dzhh.netty.handler.CommandHandler;
import top.dzhh.netty.handler.ResponseEncoder;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
@Slf4j
public class RedisApplication {
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private final RedisDb redisDb = new RedisDb();

    public RedisApplication() {
    }


    public static void main(String[] args) {
        new RedisApplication().start();
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
                        // resp编码器，返回结果
                        channelPipeline.addLast(new ResponseEncoder());
                        // 处理客户端的链接和断开，管理客户端
                        channelPipeline.addLast(new ClientHandler());
                        // 命令解析处理器，原始信息转成命令和参数
                        channelPipeline.addLast(new CommandDecoder());
                        // 处理不同的请求，针对数据库进行操作
                        channelPipeline.addLast(new CommandHandler(redisDb));
                        log.info(String.valueOf(channelPipeline.names()));
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
