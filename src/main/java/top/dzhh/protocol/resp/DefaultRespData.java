package top.dzhh.protocol.resp;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.AbstractRespData;
import top.dzhh.protocol.RespConstants;
import top.dzhh.protocol.RespData;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
public class DefaultRespData<T> extends AbstractRespData<String> {

    public DefaultRespData() {

    }

    @Override
    public RespData<String> decode(ByteBuf buffer) {
        return this.setValue("unsupported protocol");
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, RespData<String> respData, ByteBuf byteBuf) {
        try {
            byteBuf.writeByte(RespConstants.MINUS_BYTE);
            byteBuf.writeBytes(respData.getValue().getBytes(StandardCharsets.UTF_8));
            byteBuf.writeBytes(RespConstants.CRLF);
        } catch (Exception e) {
            channelHandlerContext.close();
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
