package top.dzhh.commamd;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.RespData;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public interface RedisCommand {

    CommandType type();

    void setContent(RespData<?>[] array);

    void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command);

}
