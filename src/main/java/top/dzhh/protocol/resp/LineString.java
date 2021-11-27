package top.dzhh.protocol.resp;

import io.netty.buffer.ByteBuf;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
public class LineString extends AbstractResp {
    String value;

    public LineString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Resp decode(ByteBuf buffer) {
        return new LineString(readLine(buffer));
    }

    @Override
    public String toString() {
        return this.value;
    }
}
