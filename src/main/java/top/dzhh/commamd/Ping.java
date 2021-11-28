package top.dzhh.commamd;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespSimpleString;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class Ping implements Command {
    private Resp<?>[] array;

    @Override
    public CommandType type() {
        return CommandType.ping;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.array = array;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, Command command) {
        if (array.length == 1) {
            ctx.writeAndFlush(new RespSimpleString<String>("PONG"));
        } else if (array.length > 1) {
            ctx.writeAndFlush(new RespSimpleString<String>(array[1].toString()));
        }
    }
}
