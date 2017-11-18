package ru.kvlt.testtask.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import ru.kvlt.testtask.utils.Log;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FlexDecoder extends ByteToMessageDecoder {

    private static final int MAX_BYTES = 65535;
    private static final int MIN_BYTES = 16;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readableBytes();

        if (length > MAX_BYTES || length < MIN_BYTES) {
            return;
        }

        ByteBuf head = byteBuf.slice(0, 16);
        ByteBuf body = byteBuf.readSlice(16);

        byte[] sizeBytes = ByteBufUtil.getBytes(byteBuf.slice(12, 14));
        int size = ((sizeBytes[0] << 8) & 0x0000ff00) | (sizeBytes[1] & 0x000000ff);

        System.out.println(size);

        String headStr = ByteBufUtil.prettyHexDump(head);
        String bodyStr = ByteBufUtil.prettyHexDump(body);

        ByteBuf preamble = head.slice(0, 4);
        String preamleStr = preamble.toString(Charset.forName("UTF-8"));
        Log.print(preamleStr);

        //Log.print("<HEAD>\n" + headStr);
        //Log.print("<BODY>\n" + bodyStr);
        //Log.print("\n");

    }

}
