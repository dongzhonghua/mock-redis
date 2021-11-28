package top.dzhh.protocol.resp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class LineString<T> extends AbstractResp<String> {

    public LineString(String value) {
        super.setValue(value);
    }

    @Override
    public Resp<String> decode(ByteBuf buffer) {
        return new LineString<String>(readLine(buffer));
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Resp<String> resp, ByteBuf byteBuf) {
        throw new UnsupportedOperationException();
    }


    @Override
    public String toString() {
        return this.value;
    }
}
