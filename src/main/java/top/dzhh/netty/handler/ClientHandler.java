package top.dzhh.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.model.RedisClient;
import top.dzhh.model.RedisServer;

/**
 * @author dongzhonghua
 * Created on 2022-06-10
 */
@Slf4j
@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RedisClient client = RedisClient.builder()
                .address(ctx.channel().remoteAddress())
                .channel(ctx.channel())
                .build();
        RedisServer.addClient(ctx.channel(), client);
        log.info("客户端上线 {}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端下线 {}", ctx.channel());
        RedisServer.removeChannelByName(ctx.channel());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(String.valueOf(cause));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RedisClient client = RedisServer.getClientByChannel(ctx.channel());
        ByteBuf buf = (ByteBuf) msg;
        client.setBuf(buf);
        System.out.println(buf.refCnt());
        ctx.fireChannelRead(buf);
    }
}
