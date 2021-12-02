package top.dzhh;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.redis.core.RedisCore;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
@Slf4j
public class CommandHandler extends SimpleChannelInboundHandler<RedisCommand> {
    // TODO: 2021/11/28 aof
    private final RedisCore redisCore;

    public CommandHandler(RedisCore redisCore) {
        this.redisCore = redisCore;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RedisCommand command) {
        log.info("--------command handler-------------");
        log.info(command.type().name());
        try {
            command.handle(channelHandlerContext, redisCore, command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
