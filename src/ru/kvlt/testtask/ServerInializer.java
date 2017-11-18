package ru.kvlt.testtask;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import ru.kvlt.testtask.network.FlexDecoder;
import ru.kvlt.testtask.network.FlexEncoder;
import ru.kvlt.testtask.network.FlexHandler;

public class ServerInializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(
                new FlexEncoder(),
                new FlexDecoder(),
                new FlexHandler()
        );
    }

}
