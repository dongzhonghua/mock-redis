package top.dzhh.commamd.hash;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisHash;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataBulkString;
import top.dzhh.protocol.resp.RespDataError;
import top.dzhh.protocol.resp.RespDataInteger;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-30
 */
@Slf4j
public class Hset implements RedisCommand {
    protected String key;
    protected List<Pair<String, String>> fields = new ArrayList<>();

    @Override
    public CommandType type() {
        return CommandType.hset;
    }

    @Override
    public void setContent(RespData<?>[] array) {
        this.key = ((RespDataBulkString<String>) array[1]).getValue();
        int index = 2;
        while (index < array.length && (array.length - index) % 2 == 0) {
            String field = ((RespDataBulkString<String>) array[index++]).getValue();
            String value = ((RespDataBulkString<String>) array[index++]).getValue();
            fields.add(new Pair<>(field, value));
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        RedisData redisData = redisDb.get(key);
        if (redisData == null) {
            RedisHash redisHash = new RedisHash();
            long count = fields.stream().map(f -> redisHash.put(f.getKey(), f.getValue())).mapToInt(a -> a).sum();
            redisDb.put(key, redisHash);
            ctx.writeAndFlush(new RespDataInteger<Long>(count));
        } else if (redisData instanceof RedisHash) {
            long count = fields.stream().map(f -> ((RedisHash) redisData).put(f.getKey(), f.getValue()))
                    .mapToInt(a -> a).sum();
            redisDb.put(key, redisData);
            ctx.writeAndFlush(new RespDataInteger<Long>(count));
        } else {
            ctx.writeAndFlush(new RespDataError<String>("not hash"));
            log.error("type error");
        }

    }
}
