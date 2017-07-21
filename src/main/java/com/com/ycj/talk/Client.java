package com.com.ycj.talk;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.List;

public class Client {

    private static final String id = "1";

    private static final List<String> friends = Lists.newArrayList();
    static {

        friends.add("2");
    }

    private static ChannelHandlerContext ctx;


    public static void main(String[] args) {
        startServer();




        //sned();
    }


    public static void startServer() {
        ClientHandler clientHandler =new ClientHandler(id);
        EventLoopGroup boss = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(boss);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline p = channel.pipeline();
                p.addLast(new StringDecoder());
                p.addLast(new JsonObjectDecoder());
                p.addLast(clientHandler);
            }
        });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8080).sync();
            channelFuture.addListener(future ->{
                if(future.isSuccess()){
                    ctx=clientHandler.getContext();
                    sned();
                }

            });

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
        }


    }



    public static  void sendMsg(Content content){
        ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSON(content).toString().getBytes()));
    }

    public static void sned(){
        new Thread(()->{
            while(true){

               // System.out.println("user1 开发发消息");
                Content content = new Content();
                content.setCommend(Commend.TALK);
                content.setUserId(id);
                content.setFriendId(friends.get(0));
                content.setContent("你好朋友2");
                if(content!=null)
                    sendMsg(content);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
