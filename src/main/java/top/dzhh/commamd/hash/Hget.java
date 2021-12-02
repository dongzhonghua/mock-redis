package top.dzhh.commamd.hash;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisHash;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-30
 */
@Slf4j
public class Hget implements RedisCommand {
    private String key;
    private String field;

    @Override
    public CommandType type() {
        return CommandType.hget;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        key = ((RespBulkString<String>) array[1]).getValue();
        field = ((RespBulkString<String>) array[2]).getValue();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null) {
            ctx.writeAndFlush(RespBulkString.NULL_BULK_STRING);
            return;
        }
        String value = ((RedisHash) redisData).get(field);
        ctx.writeAndFlush(new RespBulkString<String>(value));
    }
}
