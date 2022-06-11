package top.dzhh.commamd;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.RespData;
import top.dzhh.protocol.resp.RespDataArray;
import top.dzhh.protocol.resp.RespDataBulkString;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
@Slf4j
public class CommandFactory {
    static Map<String, Supplier<RedisCommand>> map = new HashMap<>();

    static {
        for (CommandType each : CommandType.values()) {
            map.put(each.name(), each.getSupplier());
        }
    }

    /**
     * 返回一个redis请求对应的命令处理器
     * @param resp 都是RespArray
     * @param ctx
     * @return redis命令处理器，需要把请求内容set进去
     */
    public static RedisCommand getRespCommand(RespDataArray<RespData<?>[]> resp, ChannelHandlerContext ctx) {
        RespData<?>[] array = resp.getValue();
        String commandName = ((RespDataBulkString<String>) array[0]).getValue().toLowerCase();
        Supplier<RedisCommand> supplier = map.get(commandName);
        if (supplier == null) {
            log.info("不支持的命令：" + commandName);
            return null;
        } else {
            try {
                RedisCommand command = supplier.get();
                command.setContent(array);
                return command;
            } catch (Throwable e) {
                // TODO: 2021/12/2 不能直接返回null
                e.printStackTrace();
                return null;
            }
        }
    }
}
