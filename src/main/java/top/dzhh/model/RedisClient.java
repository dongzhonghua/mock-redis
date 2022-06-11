package top.dzhh.model;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Data;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2022-06-10
 */
@Data
@Builder
public class RedisClient {
    SocketAddress address;
    Channel channel;
    /**
     * 由客户端来设置名字
     */
    String name;
    /**
     * 客户端选择数据库，默认是第0个
     */
    RedisDb redisDb;
    /**
     * 保存客户端当前的命令
     */
    RedisCommand redisCommand;
    /**
     * 客户端是否通过了身份验证
     */
    boolean authenticated;
    /**
     * 输入输出缓冲区
     */
    ByteBuf buf;
}
