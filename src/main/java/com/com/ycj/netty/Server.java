package com.com.ycj.netty;

import com.com.ycj.netty.handler.HttpSnoopServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by Mtime on 2017/7/19.
 */
public class Server {


    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(eventLoopGroup, worker)
                 .option(ChannelOption.TCP_NODELAY, true)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(

                         new ChannelInitializer<NioSocketChannel>() {
                             @Override
                             protected void initChannel(NioSocketChannel nioServerSocketChannel) throws Exception {

                                 ChannelPipeline p = nioServerSocketChannel.pipeline();

                                 p.addLast(new HttpServerCodec());
                                 p.addLast(new HttpServerExpectContinueHandler());
                                 //p.addLast(new HttpHelloWorldServerHandler());
                                 p.addLast(new HttpSnoopServerHandler());
                                 //p.addLast(new ChannelHandler());

                             }
                         }

                 );
        try {
            ChannelFuture channelFuture = bootstrap.bind("127.0.0.1", 8080)
                                                   .sync();
            channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("服务器启动。。。");
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


    static class ChannelHandler extends SimpleChannelInboundHandler {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
            System.out.println("server:" + msg);
            channelHandlerContext.write(msg);
            channelHandlerContext.flush();
        }
    }


}
