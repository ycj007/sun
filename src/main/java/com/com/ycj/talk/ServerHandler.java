package com.com.ycj.talk;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ServerHandler extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline p = channel.pipeline();

        p.addLast(new JsonObjectDecoder());
        p.addLast(new StringDecoder());
        p.addLast(new TalkHandler());
    }
}
