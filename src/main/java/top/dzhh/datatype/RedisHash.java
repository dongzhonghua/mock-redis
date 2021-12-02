package top.dzhh.datatype;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzhonghua
 * Created on 2021-11-28
 */
public class RedisHash implements RedisData {
    private long timeout = -1;

    private Map<String, String> map = new HashMap<>();

    public int put(String field, String value) {
        boolean b = map.get(field) == null;
        boolean b1 = map.put(field, value) == null;
        return b && b1 ? 1 : 0;
    }


    @Override
    public long timeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String get(String field) {
        return map.get(field);
    }
}
