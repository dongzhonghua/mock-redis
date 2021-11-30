package top.dzhh.commamd;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.Resp;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public interface RedisCommand {

    CommandType type();

    void setContent(Resp<?>[] array);

    void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command);

}
