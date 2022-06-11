package top.dzhh.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractResp;

/**
 * @author dongzhonghua
 * Created on 2021-11-27
 */
@Slf4j
public class ResponseEncoder extends MessageToByteEncoder<AbstractResp<?>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractResp resp, ByteBuf byteBuf)
            throws Exception {
        log.info("--------response encoder------------");
        log.info("返回值：{}", resp);
        resp.encode(channelHandlerContext, resp, byteBuf);
        // channelHandlerContext.writeAndFlush(byteBuf); // 为什么不需要在调用这个方法，为什么调用byteBuf.write就可以。
        log.info("--------请求结束，返回客户端-----------\n");
    }
}
