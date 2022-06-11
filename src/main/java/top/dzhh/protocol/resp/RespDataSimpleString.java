package top.dzhh.protocol.resp;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractRespData;
import top.dzhh.protocol.RespConstants;
import top.dzhh.protocol.RespData;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespDataSimpleString<T> extends AbstractRespData<String> {
    public static final RespDataSimpleString<String> OK_SIMPLE_STRING = new RespDataSimpleString<>("OK");

    public RespDataSimpleString(String value) {
        super.setValue(value);
    }

    public RespDataSimpleString() {

    }

    @Override
    public RespData<String> decode(ByteBuf buffer) {
        return this.setValue(readLine(buffer));
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, RespData<String> respData, ByteBuf byteBuf) {
        try {
            byteBuf.writeByte(RespConstants.PLUS_BYTE);
            byteBuf.writeBytes(this.value.getBytes(StandardCharsets.UTF_8));
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
