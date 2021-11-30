package top.dzhh.protocol.resp;

import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespConstants;

/**
 * @author dongzhonghua
 * Created on 2021-11-30
 */
class RespBulkStringTest {

    @Test
    void decode() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        // +OK\r\n
        buffer.writeBytes("-1".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        Resp<String> decode = new RespBulkString<String>().decode(buffer);
        System.out.println(decode);
        System.out.println(RespBulkString.NULL_BULK_STRING);
    }

    @Test
    void encode() {
    }
}