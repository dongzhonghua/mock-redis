package top.dzhh.protocol.resp;

import io.netty.buffer.ByteBuf;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
public class DefaultResp extends AbstractResp {
    @Override
    public Resp decode(ByteBuf buffer) {
        throw new RuntimeException("不支持的命令类型");
    }
}
