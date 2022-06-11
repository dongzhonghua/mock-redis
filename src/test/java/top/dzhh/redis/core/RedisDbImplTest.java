package top.dzhh.redis.core;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.jupiter.api.Test;

import top.dzhh.datatype.RedisData;
import top.dzhh.datatype.RedisString;

/**
 * @author dongzhonghua
 * Created on 2021-11-30
 */
class RedisDbImplTest {

    private Map<String, RedisData> map = new ConcurrentSkipListMap<>(Comparator.reverseOrder());


    @Test
    void put() {
        RedisString rs = new RedisString();
        map.put("a", rs);
        map.put("d", rs);
        map.put("c", rs);
        map.put("b", rs);

        for (Entry<String, RedisData> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}