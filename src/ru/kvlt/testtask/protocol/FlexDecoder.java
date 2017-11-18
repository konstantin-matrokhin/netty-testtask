package ru.kvlt.testtask.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import ru.kvlt.testtask.utils.DataUtils;
import ru.kvlt.testtask.utils.Log;

import javax.xml.crypto.Data;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FlexDecoder extends ByteToMessageDecoder {

    private static final int MAX_BYTES = 65535;
    private static final int MIN_BYTES = 4;
    private static final int FLEX_HEAD_SIZE = 16;
    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readable = byteBuf.readableBytes();

        if (readable < MIN_BYTES || readable > MAX_BYTES) {
            return;
        }

        ByteBuf msgTypeByte = byteBuf.slice(0, 1);
        String msgType = msgTypeByte.toString(CHARSET).trim();

        if (msgType.startsWith("@")) {
            decodeFLEX(byteBuf);
            return;
        }
        if (msgType.startsWith("~")) {
            decodeNTCB(byteBuf);
            return;
        }
        if (msgTypeByte.getByte(0) == 0x7F) {
            decodePing(byteBuf);
        }

    }

    private void decodePing(ByteBuf byteBuf) {
        Log.info("PING");
    }

    private void decodeFLEX(ByteBuf byteBuf) {
        ByteBuf head = byteBuf.slice(0, FLEX_HEAD_SIZE);
        ByteBuf body = byteBuf.readSlice(FLEX_HEAD_SIZE); //FROM HEAD_SIZE END

        ByteBuf preamble = head.slice(0, 4);
        ByteBuf IDr = head.slice(4, 4);
        ByteBuf IDs = head.slice(8, 4);

        String preamleStr = preamble.toString(CHARSET);
        int to = DataUtils.bufToInt(IDr);
        int from = DataUtils.bufToInt(IDs);

        Log.print("Preamble: " + preamleStr);
        Log.print("To: " + to);
        Log.print("From: " + from);
        Log.print("Body:");
        Log.print(ByteBufUtil.prettyHexDump(body));
        Log.print("\n");
    }

    private void decodeNTCB(ByteBuf byteBuf) {
        Log.info("not a flex msg");
    }

}
