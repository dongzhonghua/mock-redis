package top.dzhh.commamd.hash;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisHash;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespError;
import top.dzhh.protocol.resp.RespSimpleString;
import top.dzhh.redis.core.RedisCore;

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
    public void setContent(Resp<?>[] array) {
        super.setContent(array);
    }


    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null) {
            RedisHash redisHash = new RedisHash();
            fields.stream().map(f -> redisHash.put(f.getKey(), f.getValue()));
            redisCore.put(key, redisHash);
            ctx.writeAndFlush(RespSimpleString.OK_SIMPLE_STRING);
        } else if (redisData instanceof RedisHash) {
            fields.stream().map(f -> ((RedisHash) redisData).put(f.getKey(), f.getValue()));
            redisCore.put(key, redisData);
            ctx.writeAndFlush(RespSimpleString.OK_SIMPLE_STRING);
        } else {
            ctx.writeAndFlush(new RespError<String>("not hash"));
            log.error("type error");
        }
    }
}
