package top.dzhh.commamd.string;

import static top.dzhh.protocol.resp.RespBulkString.NULL_BULK_STRING;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisString;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class Get implements RedisCommand {
    private String key;

    @Override
    public CommandType type() {
        return CommandType.set;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.key = ((RespBulkString<String>) array[1]).getValue();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null) {
            ctx.writeAndFlush(NULL_BULK_STRING);
        }
        if (redisData instanceof RedisString) {
            ctx.writeAndFlush(new RespBulkString<>(((RedisString) redisData).getValue()));
        } else {
            ctx.writeAndFlush(NULL_BULK_STRING);
        }
    }
}