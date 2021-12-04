package top.dzhh.datatype;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongzhonghua
 * Created on 2021-11-28
 */
public class RedisList implements RedisData {
    private long timeout = -1;

    LinkedList<String> list = new LinkedList<>();

    @Override
    public long timeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int lpush(String... values) {
        for (String value : values) {
            list.addFirst(value);
        }
        return size();
    }

    public int lpush(List<String> values) {
        for (String value : values) {
            list.addFirst(value);
        }
        return size();
    }

    public int rpush(List<String> values) {
        for (String value : values) {
            list.addLast(value);
        }
        return size();
    }

    public List<String> lrang(int start, int end) {
        return list.stream().skip(start).limit(end - start >= 0 ? end - start + 1 : 0).collect(Collectors.toList());
    }

    public int size() {
        return list.size();
    }

    public int remove(String value) {
        int count = 0;
        while (list.remove(value)) {
            count++;
        }
        return count;
    }

    public String get(int index) {
        return list.get(index);
    }
}
