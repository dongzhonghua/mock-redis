package top.dzhh.commamd.string;

import io.netty.channel.ChannelHandlerContext;
import top.dzhh.commamd.Command;
import top.dzhh.commamd.CommandType;
import top.dzhh.protocol.Resp;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class Set implements Command {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public void setContent(Resp<?>[] array) {

    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, Command command) {
        // RespBulkString<String> foo = new RespBulkString<>("foo");
        // RespInteger<Long> longa = new RespInteger<>(100L);
        // RespError<String> error = new RespError<>("aa");
        // RespSimpleString<String> simple = new RespSimpleString<>("simple");
        // ctx.writeAndFlush(new RespArray<Resp<?>>(new Resp[]{foo, longa, error, simple}));

    }
}
