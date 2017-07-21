package com.com.ycj.talk;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;

@Getter
public class ClientHandler extends SimpleChannelInboundHandler {

    //private ChannelHandlerContext ctx;

    private String id ;


    public ClientHandler(String id) {
        this.id = id;
    }

    private ChannelHandlerContext context;


    @Override

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // this. ctx = ctx;
        this.context =ctx;
        Content content = new Content();
        content.setCommend(Commend.REG);
        content.setUserId(id);
        content.setContent("reg");
        ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSON(content).toString().getBytes()));
        System.out.println("连接成功 注册成功");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        if(o instanceof String){
            System.out.println(o);
        }


    }





}
