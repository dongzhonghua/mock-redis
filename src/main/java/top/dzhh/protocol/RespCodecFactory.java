package top.dzhh.protocol;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.RespConstants.RespType;
import top.dzhh.protocol.resp.DefaultResp;
import top.dzhh.protocol.resp.RespArray;
import top.dzhh.protocol.resp.RespBulkString;
import top.dzhh.protocol.resp.RespError;
import top.dzhh.protocol.resp.RespInteger;
import top.dzhh.protocol.resp.RespSimpleString;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespCodecFactory {

    static final Map<RespType, Supplier<AbstractResp<?>>> DECODERS = Maps.newConcurrentMap();
    private static final Supplier<AbstractResp<?>> DEFAULT_DECODER = DefaultResp::new;

    static {
        DECODERS.put(RespType.SIMPLE_STRING, RespSimpleString::new);
        DECODERS.put(RespType.ERROR, RespError::new);
        DECODERS.put(RespType.INTEGER, RespInteger::new);
        DECODERS.put(RespType.BULK_STRING, RespBulkString::new);
        DECODERS.put(RespType.RESP_ARRAY, RespArray::new);
    }

    // TODO: 2021/11/28 内联命令也应该实现成array的形式 http://redisdoc.com/topic/protocol.html
    public static Resp<?> decode(ByteBuf buffer) {
        return DECODERS.getOrDefault(determineReplyType(buffer), DEFAULT_DECODER).get().decode(buffer);
    }

    private static RespType determineReplyType(ByteBuf buffer) {
        byte firstByte = buffer.readByte();
        RespType respType;
        switch (firstByte) {
            case RespConstants.PLUS_BYTE:
                respType = RespType.SIMPLE_STRING;
                break;
            case RespConstants.MINUS_BYTE:
                respType = RespType.ERROR;
                break;
            case RespConstants.COLON_BYTE:
                respType = RespType.INTEGER;
                break;
            case RespConstants.DOLLAR_BYTE:
                respType = RespType.BULK_STRING;
                break;
            case RespConstants.ASTERISK_BYTE:
                respType = RespType.RESP_ARRAY;
                break;
            default: {
                throw new IllegalArgumentException("first byte:" + firstByte);
            }
        }
        return respType;
    }

    public static void main(String[] args) {
        ByteBuf buffer;
        //*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n
        buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes("*2".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("$3".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("foo".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("$3".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        buffer.writeBytes("bar".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        Resp<?> decode = RespCodecFactory.decode(buffer);
        log.info(String.valueOf(decode));
    }
}
