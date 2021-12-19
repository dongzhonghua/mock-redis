package top.dzhh.datatype;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dongzhonghua
 * Created on 2021-11-28
 */
public class RedisSet implements RedisData {
    private long timeout = -1;

    Set<String> set = new HashSet<>();

    @Override
    public long timeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int add(List<String> members) {
        return (int) members.stream().filter(set::add).count();
    }

    public Collection<String> keys() {
        return set;
    }

    public int remove(List<String> members) {
        return (int) members.stream().filter(set::remove).count();
    }


}
