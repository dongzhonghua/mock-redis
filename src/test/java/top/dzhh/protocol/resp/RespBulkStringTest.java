package top.dzhh.protocol.resp;

import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import top.dzhh.protocol.RespConstants;
import top.dzhh.protocol.RespData;

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
        RespData<String> decode = new RespDataBulkString<String>().decode(buffer);
        System.out.println(decode);
        System.out.println(RespDataBulkString.NULL_BULK_STRING);
    }

    @Test
    void encode() {
    }
}