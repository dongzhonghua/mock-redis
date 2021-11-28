package top.dzhh.protocol.resp;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespConstants;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespInteger<T> extends AbstractResp<Long> {

    public RespInteger(Long value) {
        super.setValue(value);
    }

    @Override
    public Resp<Long> decode(ByteBuf buffer) {
        return new RespInteger<Long>(readInteger(buffer));
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Resp<Long> resp, ByteBuf byteBuf) {
        try {
            byteBuf.writeByte(RespConstants.COLON_BYTE);
            Long value = resp.getValue();
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
