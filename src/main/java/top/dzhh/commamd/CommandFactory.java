package top.dzhh.commamd;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.resp.RespBulkString;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
@Slf4j
public class CommandFactory {
    static Map<String, Supplier<Command>> map = new HashMap<>();

    static {
        for (CommandType each : CommandType.values()) {
            map.put(each.name(), each.getSupplier());
        }
    }


    public static Command getRespCommand(Resp<Resp<?>[]> resp) {
        Resp<?>[] array = resp.getValue();
        String commandName = ((RespBulkString<String>) array[0]).getValue().toLowerCase();
        Supplier<Command> supplier = map.get(commandName);
        if (supplier == null) {
            log.info("不支持的命令：" + commandName);
            return null;
        } else {
            try {
                Command command = supplier.get();
                command.setContent(array);
                return command;
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
