package top.dzhh;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.dzhh.commamd.Command;
import top.dzhh.protocol.resp.RespBulkString;

/**
 * @author dongzhonghua
 * Created on 2021-11-24
 */
public class CommandHandler extends SimpleChannelInboundHandler<Command> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) throws Exception {
        System.out.println("--------command handler-------------");
        channelHandlerContext.writeAndFlush(new RespBulkString("OK"));
    }
}
