package top.dzhh.commamd.list;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisList;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespArray;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.protocol.resp.RespError;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-12-02
 */
@Slf4j
public class Lrange implements RedisCommand {
    private String key;
    private int start;
    private int stop;

    @Override
    public CommandType type() {
        return CommandType.lrange;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.key = ((RespBulkString<String>) array[1]).getValue();
        this.start = Integer.parseInt(((RespBulkString<String>) array[2]).getValue());
        this.stop = Integer.parseInt(((RespBulkString<String>) array[3]).getValue());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        RedisData redisData = redisDb.get(key);
        if (redisData == null) {
            ctx.writeAndFlush(RespBulkString.NULL_BULK_STRING);
        } else if (redisData instanceof RedisList) {
            List<String> res = ((RedisList) redisData).lrang(start, stop);
            RespBulkString<String>[] result = new RespBulkString[res.size()];
            for (int i = 0; i < res.size(); i++) {
                result[i] = new RespBulkString<>(res.get(i));
            }
            ctx.writeAndFlush(new RespArray<>(result));
        } else {
            ctx.writeAndFlush(new RespError<String>("not list"));
            log.error("type error");
        }
    }
}
