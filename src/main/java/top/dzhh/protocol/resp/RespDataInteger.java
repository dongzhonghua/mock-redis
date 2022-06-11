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
public class RespDataInteger<T> extends AbstractRespData<Long> {

    public RespDataInteger(Long value) {
        super.setValue(value);
    }

    public RespDataInteger() {

    }

    @Override
    public RespData<Long> decode(ByteBuf buffer) {
        return this.setValue(readInteger(buffer));
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, RespData<Long> respData, ByteBuf byteBuf) {
        try {
            byteBuf.writeByte(RespConstants.COLON_BYTE);
            Long value = respData.getValue();
            log.info(String.valueOf(this.value));
            String content = String.valueOf(value);
            byteBuf.writeBytes(content.getBytes(StandardCharsets.UTF_8));
            byteBuf.writeBytes(RespConstants.CRLF);
        } catch (Exception e) {
            channelHandlerContext.close();
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
