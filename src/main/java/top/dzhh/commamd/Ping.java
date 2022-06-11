package top.dzhh.commamd;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataSimpleString;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class Ping implements RedisCommand {
    private RespData<?>[] array;

    @Override
    public CommandType type() {
        return CommandType.ping;
    }

    @Override
    public void setContent(RespData<?>[] array) {
        this.array = array;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        if (array.length == 1) {
            ctx.writeAndFlush(new RespDataSimpleString<String>("PONG"));
        } else if (array.length > 1) {
            ctx.writeAndFlush(new RespDataSimpleString<String>(array[1].toString()));
        }
    }
}
