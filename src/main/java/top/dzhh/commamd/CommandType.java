package top.dzhh.commamd;

import java.util.function.Supplier;

import top.dzhh.commamd.string.Set;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public enum CommandType {
    command(StartCommand::new),
    ping(Ping::new),

    set(Set::new),


    ;

    private final Supplier<Command> supplier;

    CommandType(Supplier<Command> supplier) {
        this.supplier = supplier;
    }

    public Supplier<Command> getSupplier() {
        return supplier;
    }
}
