package top.dzhh.commamd.set;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisSet;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.protocol.resp.RespError;
import top.dzhh.protocol.resp.RespInteger;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-12-04
 */
@Slf4j
public class Sadd implements RedisCommand {
    protected String key;
    protected List<String> fields = new ArrayList<>();

    @Override
    public CommandType type() {
        return CommandType.sadd;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.key = ((RespBulkString<String>) array[1]).getValue();
        int index = 2;
        while (index < array.length) {
            String field = ((RespBulkString<String>) array[index++]).getValue();
            fields.add(field);
        }
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command) {
        RedisData redisData = redisCore.get(key);
        if (redisData == null) {
            RedisSet redisSet = new RedisSet();
            int size = redisSet.add(fields);
            redisCore.put(key, redisSet);
            ctx.writeAndFlush(new RespInteger<Long>((long) size));
        } else if (redisData instanceof RedisSet) {
            int size = ((RedisSet) redisData).add(fields);
            redisCore.put(key, redisData);
            ctx.writeAndFlush(new RespInteger<Long>((long) size));
        } else {
            ctx.writeAndFlush(new RespError<String>("not set"));
            log.error("type error");
        }

    }
}
