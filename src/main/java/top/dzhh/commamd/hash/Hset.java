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
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.protocol.resp.RespError;
import top.dzhh.protocol.resp.RespInteger;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-30
 */
@Slf4j
public class Hset implements RedisCommand {
    private String key;
    private List<Pair<String, String>> fields = new ArrayList<>();

    @Override
    public CommandType type() {
        return CommandType.hset;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.key = ((RespBulkString<String>) array[1]).getValue();
        int index = 2;
        while (index < array.length && (array.length - index) % 2 == 0) {
            String field = ((RespBulkString<String>) array[index++]).getValue();
            String value = ((RespBulkString<String>) array[index++]).getValue();
            fields.add(new Pair<>(field, value));
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null) {
            RedisHash redisHash = new RedisHash();
            long count = fields.stream().map(f -> redisHash.put(f.getKey(), f.getValue())).count();
            redisCore.put(key, redisHash);
            ctx.writeAndFlush(new RespInteger<Long>(count));
        } else if (redisData instanceof RedisHash) {
            long count = fields.stream().map(f -> ((RedisHash) redisData).put(f.getKey(), f.getValue()))
                    .mapToInt(a -> a).sum();
            redisCore.put(key, redisData);
            ctx.writeAndFlush(new RespInteger<Long>(count));
        } else {
            ctx.writeAndFlush(new RespError<String>("not hash"));
            log.error("type error");
        }

    }
}
