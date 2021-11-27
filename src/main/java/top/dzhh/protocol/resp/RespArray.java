package top.dzhh.protocol.resp;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.protocol.AbstractResp;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespCodec;
import top.dzhh.protocol.RespConstants;

/**
 * @author dongzhonghua
 * Created on 2021-11-25
 */
@Slf4j
public class RespArray extends AbstractResp {
    Resp[] value;

    public RespArray(Resp[] array) {
        this.value = array;
    }

    public Resp[] getValue() {
        return value;
    }
    @Override
    public Resp decode(ByteBuf buffer) {
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
            return new RespArray(new Resp[0]);
        }
        Resp[] res = new Resp[Math.toIntExact(len)];
        for (int i = 0; i < len; i++) {
            res[i] = RespCodec.decode(buffer);
        }
        return new RespArray(res);


    }

    @Override
    public String toString() {
        return Arrays.toString(this.value);
    }

}
