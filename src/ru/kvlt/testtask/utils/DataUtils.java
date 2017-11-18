package ru.kvlt.testtask.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataUtils {

    public static int bufToInt(ByteBuf buf) {
        return ByteBuffer
                .wrap(ByteBufUtil.getBytes(buf))
                .order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

}
