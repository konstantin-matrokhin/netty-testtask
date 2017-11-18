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
    private static final int HEAD_SIZE = 16;
    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int packetSize = getPacketSize(byteBuf);
        if (packetSize != byteBuf.readableBytes()) {
            return;
        }

        ByteBuf head = byteBuf.slice(0, 16);
        ByteBuf body = byteBuf.readSlice(16);

        ByteBuf preamble = head.slice(0, 4);
        ByteBuf IDr = head.slice(4, 8);
        ByteBuf IDs = head.slice(8, 12);
        //TODO: checksum
        //int headCheckSum = head.getByte(14) & 0xFF;

        String preamleStr = preamble.toString(CHARSET);
        Log.print(preamleStr);
    }

    private int checkSum(ByteBuf buf, int length) {
//        int tempSum;
//        int buffer = ByteBufUtil.getBytes(buf).length;
//
//        while (length-- > 0) {
//            tempSum ^= buf++;
//        }
    }

    private int getPacketSize(ByteBuf buf) {
        byte[] sizeBytes = ByteBufUtil.getBytes(buf.slice(12, 14));
        int bodySize = sizeBytes[0] | sizeBytes[1] << 8;

        return HEAD_SIZE + bodySize;;
    }

}
