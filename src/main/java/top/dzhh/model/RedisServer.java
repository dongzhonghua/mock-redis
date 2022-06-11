package top.dzhh.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import top.dzhh.redis.core.RedisDb;

/**
 * @author dongzhonghua
 * Created on 2022-06-10
 */
public class RedisServer {
    private static RedisDb redisDb;


    private static ConcurrentHashMap<Channel, RedisClient> redisClients;

    public static Map<Channel, RedisClient> getClients() {
        return redisClients;
    }

    public static RedisClient getClientByChannel(Channel channel) {
        return redisClients.get(channel);
    }

    public static void addClient(Channel channel, RedisClient client) {
        if (redisClients == null) {
            redisClients = new ConcurrentHashMap<>(128);
        }
        redisClients.put(channel, client);

    }

    public static void removeChannelByName(Channel channel) {
        redisClients.remove(channel);
    }
}
