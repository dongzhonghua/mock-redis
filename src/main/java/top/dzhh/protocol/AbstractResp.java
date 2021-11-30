package top.dzhh.protocol;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ByteProcessor;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
public abstract class AbstractResp<T> extends Resp<T> implements RespCodec<T> {

    public abstract Resp<T> decode(ByteBuf buffer);

    public abstract void encode(ChannelHandlerContext channelHandlerContext, Resp<T> resp,
            ByteBuf byteBuf);


    protected Long readInteger(ByteBuf buffer) {
        String num = readLine(buffer);
        if (num == null) {
            return null;
        }
        return Long.parseLong(num);
    }

    protected String readLine(ByteBuf buffer) {
        int endIndex = getEndIndex(buffer);
        if (endIndex == -1) {
            return null;
        }
        int startIndex = buffer.readerIndex();
        int length = endIndex - startIndex - 1;
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);
        // 重置游标为\r\n之后的第一个字节
        buffer.readerIndex(endIndex + 1);
        buffer.markReaderIndex();
        return new String(bytes, RespConstants.UTF_8);
    }

    protected int getEndIndex(ByteBuf buffer) {
        int index = buffer.forEachByte(ByteProcessor.FIND_LF); // \n
        return (index > 0 && buffer.getByte(index - 1) == RespConstants.CR) ? index : -1;
    }
}
