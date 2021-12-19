package top.dzhh.commamd;

import java.util.function.Supplier;

import top.dzhh.commamd.hash.Hget;
import top.dzhh.commamd.hash.Hmget;
import top.dzhh.commamd.hash.Hmset;
import top.dzhh.commamd.hash.Hset;
import top.dzhh.commamd.list.Lindex;
import top.dzhh.commamd.list.Lpush;
import top.dzhh.commamd.list.Lrange;
import top.dzhh.commamd.list.Rpush;
import top.dzhh.commamd.set.Sadd;
import top.dzhh.commamd.set.Sscan;
import top.dzhh.commamd.string.Get;
import top.dzhh.commamd.string.Set;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public enum CommandType {
    command(StartCommand::new),
    ping(Ping::new),

    set(Set::new),
    get(Get::new),


    hset(Hset::new),
    hmset(Hmset::new),
    hget(Hget::new),
    hmget(Hmget::new),

    lpush(Lpush::new),
    rpush(Rpush::new),
    lindex(Lindex::new),
    lrange(Lrange::new),

    sadd(Sadd::new),
    sscan(Sscan::new);

    private final Supplier<RedisCommand> supplier;

    CommandType(Supplier<RedisCommand> supplier) {
        this.supplier = supplier;
    }

    public Supplier<RedisCommand> getSupplier() {
        return supplier;
    }
}
