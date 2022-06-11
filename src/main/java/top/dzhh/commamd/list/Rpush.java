package top.dzhh.commamd.list;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisList;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataBulkString;
import top.dzhh.protocol.resp.RespDataError;
import top.dzhh.protocol.resp.RespDataInteger;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-12-02
 */
@Slf4j
public class Rpush implements RedisCommand {
    protected String key;
    protected List<String> fields = new ArrayList<>();

    @Override
    public CommandType type() {
        return CommandType.rpush;
    }

    @Override
    public void setContent(RespData<?>[] array) {
        this.key = ((RespDataBulkString<String>) array[1]).getValue();
        int index = 2;
        while (index < array.length) {
            String field = ((RespDataBulkString<String>) array[index++]).getValue();
            fields.add(field);
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        RedisData redisData = redisDb.get(key);
        if (redisData == null) {
            RedisList redisList = new RedisList();
            int size = redisList.rpush(fields);
            redisDb.put(key, redisList);
            ctx.writeAndFlush(new RespDataInteger<Long>((long) size));
        } else if (redisData instanceof RedisList) {
            int size = ((RedisList) redisData).rpush(fields);
            redisDb.put(key, redisData);
            ctx.writeAndFlush(new RespDataInteger<Long>((long) size));
        } else {
            ctx.writeAndFlush(new RespDataError<String>("not list"));
            log.error("type error");
        }

    }
}
