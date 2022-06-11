package top.dzhh.commamd;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataSimpleString;
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
    public void setContent(RespData<?>[] array) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        ctx.writeAndFlush(RespDataSimpleString.OK_SIMPLE_STRING);
    }
}
