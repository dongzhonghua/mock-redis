package top.dzhh.commamd.hash;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisHash;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataArray;
import top.dzhh.protocol.resp.RespDataBulkString;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-30
 */
@Slf4j
public class Hmget implements RedisCommand {
    private String key;
    protected List<String> fields = new ArrayList<>();

    @Override
    public CommandType type() {
        return CommandType.hmget;
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
            ctx.writeAndFlush(RespDataBulkString.NULL_BULK_STRING);
            return;
        }
        RespDataBulkString<String>[] res =new RespDataBulkString[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            res[i] = new RespDataBulkString<>(((RedisHash) redisData).get(fields.get(i)));
        }
        ctx.writeAndFlush(new RespDataArray<>(res));
    }
}
