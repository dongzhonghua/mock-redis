package top.dzhh.protocol.resp;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespConstants;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
public class DefaultResp<T> extends LineString<String> {
    public DefaultResp() {

    }

    @Override
    public Resp<String> decode(ByteBuf buffer) {
        return this.setValue("unsupported protocol");
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Resp<String> resp, ByteBuf byteBuf) {
        try {
            byteBuf.writeByte(RespConstants.MINUS_BYTE);
            byteBuf.writeBytes(resp.getValue().getBytes(StandardCharsets.UTF_8));
            byteBuf.writeBytes(RespConstants.CRLF);
        } catch (Exception e) {
            channelHandlerContext.close();
            e.printStackTrace();
        }
    }
}
