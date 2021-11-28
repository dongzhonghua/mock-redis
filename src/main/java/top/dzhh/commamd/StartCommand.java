package top.dzhh.commamd;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.Resp;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class StartCommand implements Command {
    @Override
    public CommandType type() {
        return CommandType.ping;
    }

    @Override
    public void setContent(Resp<?>[] array) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, Command command) {
        ctx.close();
    }
}
