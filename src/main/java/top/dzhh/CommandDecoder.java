package top.dzhh;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import top.dzhh.commamd.CommandFactory;
import top.dzhh.commamd.RedisCommand;
import top.dzhh.protocol.Resp;
import top.dzhh.protocol.RespCodecFactory;
import top.dzhh.protocol.resp.RespArray;
import top.dzhh.protocol.resp.RespError;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
@Slf4j
public class CommandDecoder extends LengthFieldBasedFrameDecoder {
    public CommandDecoder() {
        this(Integer.MAX_VALUE, 0, 0);
    }

    public CommandDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        log.info("--------command decoder-------------");
        if (in.readableBytes() <= 0) {
            return null;
        }
        Resp<?> resp = RespCodecFactory.decode(in);
        log.info(String.valueOf(resp));
        RedisCommand command = null;
        if (!(resp instanceof RespArray)) {
            // 相当于可以跳过下面的handler，直接匹配传参的handler，也就是直接跳到了ResponseEncoder
            ctx.writeAndFlush(new RespError<String>("bad request"));
        } else {
            RespArray<Resp<?>[]> respArray = (RespArray<Resp<?>[]>) resp;
            command = CommandFactory.getRespCommand(respArray, ctx);
            if (command == null) {
                ctx.writeAndFlush(new RespError<String>("unsupported command: " + (respArray).getValue()[0]));
            }
        }
        return command;
    }
}
