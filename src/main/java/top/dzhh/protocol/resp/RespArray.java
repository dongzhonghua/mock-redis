package top.dzhh.protocol.resp;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespCodec;
import top.dzhh.protocol.RespCodecFactory;
import top.dzhh.protocol.RespConstants;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespArray<T> extends AbstractResp<Resp<?>[]> {

    public RespArray(Resp<?>[] array) {
        super.setValue(array);
    }

    public RespArray() {

    }

    @Override
    public Resp<Resp<?>[]> decode(ByteBuf buffer) {
        int endIndex = getEndIndex(buffer);
        if (-1 == endIndex) {
            return null;
        }
        // 解析元素个数
        Long len = readInteger(buffer);
        // null || Null Array
        if (len == null || RespConstants.NEGATIVE_ONE.equals(len)) {
            return null;
        }
        // Array Empty List
        if (RespConstants.ZERO.equals(len)) {
            return this.setValue(new Resp[0]);
        }
        Resp<?>[] res = new Resp[Math.toIntExact(len)];
        for (int i = 0; i < len; i++) {
            res[i] = RespCodecFactory.decode(buffer);
        }
        return this.setValue(res);
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Resp<Resp<?>[]> resp, ByteBuf byteBuf) {
        byteBuf.writeByte(RespConstants.ASTERISK_BYTE);
        Resp<?>[] array = resp.getValue();
        byteBuf.writeBytes(String.valueOf(array.length).getBytes(StandardCharsets.UTF_8));
        byteBuf.writeBytes(RespConstants.CRLF);
        for (Resp<?> each : array) {
            ((RespCodec) each).encode(channelHandlerContext, each, byteBuf);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.value);
    }

}
