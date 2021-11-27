package top.dzhh;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import top.dzhh.commamd.Command;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespCodec;
import top.dzhh.protocol.resp.RespArray;
import top.dzhh.protocol.resp.RespSimpleString;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
public class CommandDecoder extends LengthFieldBasedFrameDecoder {
    public CommandDecoder() {
        this(Integer.MAX_VALUE, 0, 0);
    }

    public CommandDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        System.out.println("--------command decoder-------------");
        if (in.readableBytes() <= 0) {
            return null;
        }

        Resp resp = RespCodec.decode(in);
        if (!(resp instanceof RespArray || resp instanceof RespSimpleString)) {
            throw new IllegalStateException("only support array and string");
        }
        System.out.println(resp);
        return new Command();
    }

}
