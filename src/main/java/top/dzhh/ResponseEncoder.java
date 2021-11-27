package top.dzhh;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import top.dzhh.protocol.Resp;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
public class ResponseEncoder extends MessageToByteEncoder<Resp> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Resp resp, ByteBuf byteBuf) throws Exception {
        System.out.println("--------response encoder------------");
        String content = "+OK\r\n";
        byteBuf.writeBytes(content.getBytes(StandardCharsets.UTF_8));
        byteBuf.writeBytes(byteBuf);
    }
}
