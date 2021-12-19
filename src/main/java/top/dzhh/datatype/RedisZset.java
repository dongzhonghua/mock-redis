package top.dzhh.datatype;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dongzhonghua
 * Created on 2021-11-28
 */
public class RedisZset implements RedisData {
    private long timeout = -1;

    private TreeMap<ZsetKey, Long> map = new TreeMap<>(Comparator.comparingLong(k -> k.score));

    @Override
    public long timeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int add(List<ZsetKey> keys) {
        return keys.stream().map(key -> map.put(key, key.score))
                .filter(Objects::nonNull)
                .mapToInt(Math::toIntExact)
                .sum();
    }

    public int remove(List<String> members) {
        return (int) members.stream().filter(m -> map.remove(new ZsetKey(m, 0)) != null).count();
    }

    public List<ZsetKey> range(int start, int end) {
        return map.keySet().stream().skip(start).limit(end - start >= 0 ? end - start + 1 : 0)
                .collect(Collectors.toList());
    }

    public List<ZsetKey> revrange(int start, int end) {
        return map.descendingKeySet().descendingSet().stream().skip(start).limit(end - start >= 0 ? end - start + 1 : 0)
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class ZsetKey {
        String key;
        long score;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return key.equals(((ZsetKey) o).key);
        }
    }
}
