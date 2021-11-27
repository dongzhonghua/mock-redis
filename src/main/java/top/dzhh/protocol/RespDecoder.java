package top.dzhh.protocol;

import io.netty.buffer.ByteBuf;

/**
 * @author dongzhonghua
 * Created on 2021-11-26
 */
public interface RespDecoder {
    Resp decode(ByteBuf buffer);
}
