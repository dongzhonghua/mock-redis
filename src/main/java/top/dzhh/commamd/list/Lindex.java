package top.dzhh.commamd.list;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisList;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.protocol.resp.RespError;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-12-02
 */
@Slf4j
public class Lindex implements RedisCommand {
    private String key;
    private int index;

    @Override
    public CommandType type() {
        return CommandType.lindex;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.key = ((RespBulkString<String>) array[1]).getValue();
        this.index = Integer.parseInt(((RespBulkString<String>) array[2]).getValue());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null) {
            ctx.writeAndFlush(RespBulkString.NULL_BULK_STRING);
        } else if (redisData instanceof RedisList) {
            String res = ((RedisList) redisData).get(index);
            ctx.writeAndFlush(new RespBulkString<String>(res));
        } else {
            ctx.writeAndFlush(new RespError<String>("not list"));
            log.error("type error");
        }
    }
}
