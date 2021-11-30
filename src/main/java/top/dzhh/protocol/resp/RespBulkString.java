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
public class RespBulkString<T> extends AbstractResp<String> {
    public static final RespBulkString<String> NULL_BULK_STRING = new RespBulkString<>(null);

    public RespBulkString(String value) {
        super.setValue(value);
    }

    public RespBulkString() {

    }

    @Override
    public Resp<String> decode(ByteBuf buffer) {
        int endIndex = getEndIndex(buffer);
        if (-1 == endIndex) {
            return null;
        }
        Long len = readInteger(buffer);
        // null || Bulk Null String
        if (len == null || RespConstants.NEGATIVE_ONE.equals(len)) {
            return null;
        }
        // Bulk Empty String
        if (RespConstants.ZERO.equals(len)) {
            return this.setValue(RespConstants.EMPTY_STRING);
        }
        if (buffer.readableBytes() < len + 2) {
            return null;
        }
        if (getEndIndex(buffer) - endIndex - 2 != len) {
            throw new RuntimeException("协议解析出错，字符串长度有误");
        }
        return this.setValue(readLine(buffer));
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Resp<String> resp, ByteBuf byteBuf) {
        byteBuf.writeByte(RespConstants.DOLLAR_BYTE);
        String value = resp.getValue();
        if (value == null) {
            byteBuf.writeByte(RespConstants.MINUS_BYTE);
            byteBuf.writeByte('1');
            byteBuf.writeBytes(RespConstants.CRLF);
        } else if (value.getBytes(StandardCharsets.UTF_8).length == 0) {
            byteBuf.writeByte(RespConstants.ZERO.byteValue());
            byteBuf.writeBytes(RespConstants.CRLF);
            byteBuf.writeBytes(RespConstants.CRLF);
        } else {
            String length = String.valueOf(value.getBytes(StandardCharsets.UTF_8).length);
            char[] charArray = length.toCharArray();
            for (char each : charArray) {
                byteBuf.writeByte((byte) each);
            }
            byteBuf.writeBytes(RespConstants.CRLF);
            byteBuf.writeBytes(value.getBytes(StandardCharsets.UTF_8));
            byteBuf.writeBytes(RespConstants.CRLF);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
