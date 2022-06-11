package top.dzhh.commamd.list;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisList;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataBulkString;
import top.dzhh.protocol.resp.RespDataError;
import top.dzhh.redis.core.RedisDb;

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
    public void setContent(RespData<?>[] array) {
        this.key = ((RespDataBulkString<String>) array[1]).getValue();
        this.index = Integer.parseInt(((RespDataBulkString<String>) array[2]).getValue());
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisDb redisDb, RedisCommand command) {
        RedisData redisData = redisDb.get(key);
        if (redisData == null) {
            ctx.writeAndFlush(RespDataBulkString.NULL_BULK_STRING);
        } else if (redisData instanceof RedisList) {
            String res = ((RedisList) redisData).get(index);
            ctx.writeAndFlush(new RespDataBulkString<String>(res));
        } else {
            ctx.writeAndFlush(new RespDataError<String>("not list"));
            log.error("type error");
        }
    }
}
