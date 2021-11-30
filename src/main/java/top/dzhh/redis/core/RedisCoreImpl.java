package top.dzhh.redis.core;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import top.dzhh.datatype.RedisData;

/**
 * @author dongzhonghua
 * Created on 2021-11-28
 */
public class RedisCoreImpl implements RedisCore {
    private Map<String, RedisData> map = new ConcurrentSkipListMap<>();

    @Override
    public void put(String key, RedisData redisData) {
        map.put(key, redisData);
    }

    @Override
    public RedisData get(String key) {
        RedisData redisData = map.get(key);
        if (redisData == null) {
            return null;
        }
        if (redisData.timeout() == -1) {
            return redisData;
        }
        if (redisData.timeout() < System.currentTimeMillis()) {
            map.remove(key);
            return null;
        }
        return redisData;
    }

    @Override
    public boolean exist(String key) {
        return map.containsKey(key);
    }
}
