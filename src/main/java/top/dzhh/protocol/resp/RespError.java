package top.dzhh.protocol.resp;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespConstants;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespError<T> extends LineString<String> {

    public RespError(String value) {
        super(value);
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Resp<String> resp, ByteBuf byteBuf) {
        try {
            byteBuf.writeByte(RespConstants.MINUS_BYTE);
            String value = resp.getValue();
            log.info(value);
            byteBuf.writeBytes(value.getBytes(StandardCharsets.UTF_8));
            byteBuf.writeBytes(RespConstants.CRLF);
        } catch (Exception e) {
            channelHandlerContext.close();
            e.printStackTrace();
        }
    }
}
