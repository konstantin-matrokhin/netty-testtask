package ru.kvlt.testtask.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import ru.kvlt.testtask.Log;

import java.io.IOException;
import java.util.List;

public class FlexDecoder extends ByteToMessageDecoder {

    private static final int MAX_BYTES = 65551;
    private static final int MIN_BYTES = 16;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readableBytes();

        if (length > MAX_BYTES || length < MIN_BYTES) {
            return;
        }

        ByteBuf head = byteBuf.slice(0, 15);
        ByteBuf body = byteBuf.readSlice(16);

        String headStr = ByteBufUtil.prettyHexDump(head);
        String bodyStr = ByteBufUtil.prettyHexDump(body);

        Log.print("<HEAD>\n" + headStr);
        Log.print("<BODY>\n" + bodyStr);
        Log.print("\n");
    }

}
