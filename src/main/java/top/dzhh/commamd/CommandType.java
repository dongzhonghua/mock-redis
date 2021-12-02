package top.dzhh.commamd;

import java.util.function.Supplier;

import top.dzhh.commamd.hash.Hget;
import top.dzhh.commamd.hash.Hset;
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
    hget(Hget::new);

    private final Supplier<RedisCommand> supplier;

    CommandType(Supplier<RedisCommand> supplier) {
        this.supplier = supplier;
    }

    public Supplier<RedisCommand> getSupplier() {
        return supplier;
    }
}
