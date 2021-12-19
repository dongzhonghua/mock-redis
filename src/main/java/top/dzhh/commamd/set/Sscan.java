package top.dzhh.commamd.set;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandType;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-12-04
 */
@Slf4j
public class Sscan implements RedisCommand {
    protected String key;

    @Override
    public CommandType type() {
        return CommandType.sscan;
    }

    @Override
    public void setContent(Resp<?>[] array) {
        this.key = ((RespBulkString<String>) array[1]).getValue();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, RedisCore redisCore, RedisCommand command) {
        // TODO: 2021/12/4 http://doc.redisfans.com/key/scan.html#scan 这个命令非常的麻烦，需要去找找Redis怎么实现的

    }
}
