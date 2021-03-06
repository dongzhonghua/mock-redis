package top.dzhh.commamd.hash;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisHash;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataError;
import top.dzhh.protocol.resp.RespDataSimpleString;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-30
 */
@Slf4j
public class Hmset extends Hset {
    @Override
    public CommandType type() {
        return CommandType.hmset;
    }

    @Override
    public void setContent(RespData<?>[] array) {
        super.setContent(array);
    }


    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        RedisData redisData = redisDb.get(key);
        if (redisData == null) {
            RedisHash redisHash = new RedisHash();
            fields.stream().map(f -> redisHash.put(f.getKey(), f.getValue()));
            redisDb.put(key, redisHash);
            ctx.writeAndFlush(RespDataSimpleString.OK_SIMPLE_STRING);
        } else if (redisData instanceof RedisHash) {
            fields.stream().map(f -> ((RedisHash) redisData).put(f.getKey(), f.getValue()));
            redisDb.put(key, redisData);
            ctx.writeAndFlush(RespDataSimpleString.OK_SIMPLE_STRING);
        } else {
            ctx.writeAndFlush(new RespDataError<String>("not hash"));
            log.error("type error");
        }
    }
}
