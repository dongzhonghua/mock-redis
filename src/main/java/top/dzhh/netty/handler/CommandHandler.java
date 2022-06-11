package top.dzhh.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.model.RedisClient;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
@Slf4j
public class CommandHandler extends SimpleChannelInboundHandler<RedisClient> {
    // TODO: 2021/11/28 aof
    private final RedisDb redisDb;

    public CommandHandler(RedisDb redisDb) {
        this.redisDb = redisDb;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RedisClient client) {
        log.info("--------command handler-------------");
        RedisCommand command = client.getRedisCommand();
        log.info("命令名称：{}", command.type().name());
        try {
            command.handle(channelHandlerContext, redisDb, command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
