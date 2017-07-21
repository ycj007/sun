package com.com.ycj.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by Mtime on 2017/7/19.
 */
public class Client {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                 .channel(NioSocketChannel.class)
                 .handler(
                         new ChannelInitializer<NioSocketChannel>() {
                             @Override
                             protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                 nioSocketChannel.pipeline()
                                                 //.addLast(new ChannelHandler())
                                                 .addLast(new EchoClientHandler());
                             }
                         }

                 );

        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080)
                                                   .sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("服务器连接成功");
                        channelFuture.channel()
                                     .write("test");
                        channelFuture.channel()
                                     .flush();
                    }

                }
            });
            channelFuture.channel()
                         .closeFuture()
                         .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }



    }


    public static class EchoClientHandler extends ChannelInboundHandlerAdapter {


        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            //ctx.writeAndFlush(Unpooled.wrappedBuffer("Hello world".getBytes()));
            ChannelFuture cf = ctx.writeAndFlush(Unpooled.wrappedBuffer("Ack".getBytes()));
            if (!cf.isSuccess()) {
                System.out.println("Send failed: " + cf.cause());
            }else{
                System.out.println("send success");
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ctx.write(msg);
            System.out.println(msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // Close the connection when an exception is raised.
            cause.printStackTrace();
            ctx.close();
        }
    }
}
