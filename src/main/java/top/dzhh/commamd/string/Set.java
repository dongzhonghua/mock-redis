package top.dzhh.commamd.string;

import static top.dzhh.protocol.resp.RespBulkString.NULL_BULK_STRING;
import static top.dzhh.protocol.resp.RespSimpleString.OK_SIMPLE_STRING;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisString;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class Set implements RedisCommand {
    private String key;
    private String value;
    private long timeout = -1;
    private boolean notExist = false;
    private boolean exist = false;

    @Override
    public CommandType type() {
        return CommandType.set;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.key = ((RespBulkString<String>) array[1]).getValue();
        this.value = ((RespBulkString<String>) array[2]).getValue();
        int index = 3;
        while (index < array.length) {
            String value = ((RespBulkString<String>) array[index]).getValue();
            index++;
            if (value.startsWith("EX")) {
                String seconds = ((RespBulkString<String>) array[index]).getValue();
                timeout = Integer.parseInt(seconds) * 1000L;
            } else if (value.startsWith("PX")) {
                String millisecond = ((RespBulkString<String>) array[index]).getValue();
                timeout = Integer.parseInt(millisecond);
            } else if (value.equals("NX")) {
                notExist = true;
            } else if (value.equals("XX")) {
                exist = true;
            }
        }

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        if (notExist && redisDb.exist(key)) {
            ctx.writeAndFlush(NULL_BULK_STRING);
        } else if (exist && !redisDb.exist(key)) {
            ctx.writeAndFlush(NULL_BULK_STRING);
        } else {
            RedisString redisString = new RedisString(value);
            if (timeout != -1) {
                timeout += System.currentTimeMillis();
                redisString.setTimeout(timeout);
            }
            redisDb.put(key, redisString);
            ctx.writeAndFlush(OK_SIMPLE_STRING);
        }
    }
}
