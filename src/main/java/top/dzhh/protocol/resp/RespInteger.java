package top.dzhh.protocol.resp;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespInteger extends AbstractResp {
    Long value;

    public RespInteger(Long value) {
        this.value = value;
    }

    @Override
    public Resp decode(ByteBuf buffer) {
        return new RespInteger(readInteger(buffer));
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
