package top.dzhh.commamd.hash;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisHash;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataBulkString;
import top.dzhh.redis.core.RedisDb;

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
    public void setContent(RespData<?>[] array) {
        key = ((RespDataBulkString<String>) array[1]).getValue();
        field = ((RespDataBulkString<String>) array[2]).getValue();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        RedisData redisData = redisDb.get(key);
        if (redisData == null) {
            ctx.writeAndFlush(RespDataBulkString.NULL_BULK_STRING);
            return;
        }
        String value = ((RedisHash) redisData).get(field);
        ctx.writeAndFlush(new RespDataBulkString<String>(value));
    }
}
