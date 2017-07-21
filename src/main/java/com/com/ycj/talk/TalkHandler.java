package com.com.ycj.talk;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Collection;

public class TalkHandler extends SimpleChannelInboundHandler {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof String) {
            String msg = o.toString();
            Content content = JSON.parseObject(msg, Content.class);
            System.out.println(content);
            Commend commend = content.getCommend();
            if (Commend.REG.equals(commend)) {
                Server.getOnlineMap()
                      .put(content.getUserId(), channelHandlerContext);
                Collection<Content> cacheMsg = Server.getMsgCache()
                                                     .get(content.getUserId());
                cacheMsg.stream()
                        .forEach(c -> {
                            Server.getOnlineMap()
                                  .get(content.getUserId())
                                  .writeAndFlush(Unpooled.wrappedBuffer(JSON.toJSON(c)
                                                                            .toString()
                                                                            .getBytes()));
                        });

            } else if (Commend.TALK.equals(commend)) {

                String friendId = content.getFriendId();
                if (Server.getOnlineMap()
                          .get(friendId) != null) {
                    Collection<Content> cacheMsg = Server.getMsgCache()
                                                         .get(friendId);
                    cacheMsg.stream()
                            .forEach(c -> {
                                Server.getOnlineMap()
                                      .get(friendId)
                                      .writeAndFlush(Unpooled.wrappedBuffer(JSON.toJSON(c).toString()
                                                                                .getBytes()));
                            });
                    Server.getOnlineMap()
                          .get(friendId)
                          .writeAndFlush(Unpooled.wrappedBuffer(JSON.toJSON(content).toString()
                                                                       .getBytes()));

                } else {
                    Server.getMsgCache()
                          .put(friendId, content);
                }


            } else if (Commend.ADD_FRIEND.equals(commend)) {

            } else {
                channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("failed".getBytes()));
            }


        }


    }
}
