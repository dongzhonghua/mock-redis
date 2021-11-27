package top.dzhh.protocol.resp;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespConstants;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespBulkString extends AbstractResp {
    String value;

    public RespBulkString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Resp decode(ByteBuf buffer) {
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
            return new RespBulkString(RespConstants.EMPTY_STRING);
        }
        if (buffer.readableBytes() < len + 2) {
            return null;
        }
        if (getEndIndex(buffer) - endIndex - 2 != len) {
            throw new RuntimeException("协议解析出错，字符串长度有误");
        }
        return new RespBulkString(readLine(buffer));
    }

    @Override
    public String toString() {
        return this.value;
    }
}
