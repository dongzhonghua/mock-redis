package top.dzhh.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandFactory;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.model.RedisClient;
import top.dzhh.protocol.RespCodecFactory;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataArray;
import top.dzhh.protocol.resp.RespDataError;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
@Slf4j
public class CommandDecoder extends SimpleChannelInboundHandler<RedisClient> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RedisClient client) {
        log.info("--------command decoder-------------");

        ByteBuf in = client.getBuf();
        if (in.readableBytes() <= 0) {
            ctx.fireChannelRead(null);
        }
        RespData<?> respData = RespCodecFactory.decode(in);
        log.info("命令和参数：{}", respData);
        RedisCommand command = null;
        if (!(respData instanceof RespDataArray)) {
            // 相当于可以跳过下面的handler，直接匹配传参的handler，也就是直接跳到了ResponseEncoder
            ctx.writeAndFlush(new RespDataError<String>("bad request"));
        } else {
            RespDataArray<RespData<?>[]> respArray = (RespDataArray<RespData<?>[]>) respData;
            command = CommandFactory.getRespCommand(respArray, ctx);
            if (command == null) {
                ctx.writeAndFlush(new RespDataError<String>("unsupported command: " + (respArray).getValue()[0]));
            }
        }
        client.setRedisCommand(command);
        ctx.fireChannelRead(client);
    }
}
