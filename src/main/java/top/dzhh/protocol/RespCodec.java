package top.dzhh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author dongzhonghua
 * Created on 2021-11-26
 */
public interface RespCodec<T> {
    Resp<T> decode(ByteBuf buffer);

    void encode(ChannelHandlerContext channelHandlerContext, Resp<T> resp, ByteBuf byteBuf);
}
