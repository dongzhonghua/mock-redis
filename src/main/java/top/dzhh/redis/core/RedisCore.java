package top.dzhh.redis.core;

import top.dzhh.datatype.RedisData;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public interface RedisCore {
    void put(String  key, RedisData redisData);
    RedisData get(String key);

    boolean exist(String key);
}
