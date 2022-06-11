package top.dzhh.commamd;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespSimpleString;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class StartCommand implements RedisCommand {
    @Override
    public CommandType type() {
        return CommandType.command;
    }

    @Override
    public void setContent(Resp<?>[] array) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        ctx.writeAndFlush(RespSimpleString.OK_SIMPLE_STRING);
    }
}
