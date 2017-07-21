package com.com.ycj.talk;

import com.google.common.collect.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class Server {

    private static final List<String> users = Lists.newCopyOnWriteArrayList();
    private static final Set<String> online = Sets.newConcurrentHashSet();

    private static final Multimap userFriends = LinkedListMultimap.create();
    private static final Map<String,ChannelHandlerContext> onlineMap = Maps.newConcurrentMap();

    private static  final Multimap<String,Content> msgCache =  ArrayListMultimap.create();


    static {
        IntStream.range(1, 10)
                 .forEach(index -> {
                     users.add(index + "");
                 });
        users.forEach(user -> {
            userFriends.put(1, 2);
            userFriends.put(2, 1);
        });
    }

    public static void main(String[] args) {
        startServer();
    }


    public static void startServer() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ServerHandler());
        try {
            ChannelFuture future = bootstrap.bind(8080)
                                            .sync();
            future.addListener(
                    (f) -> {
                        String content = "";
                        if (f.isSuccess()) {
                            content = "server started";
                        }
                        if (f.isCancelled()) {
                            content = "server cancelled";
                        }

                        System.out.println(content);

                    });
            future.channel()
                  .closeFuture()
                  .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }

    public static List<String> getUsers() {
        return users;
    }

    public static Set<String> getOnline() {
        return online;
    }

    public static Multimap getUserFriends() {
        return userFriends;
    }

    public static Map<String, ChannelHandlerContext> getOnlineMap() {
        return onlineMap;
    }

    public static Multimap<String, Content> getMsgCache() {
        return msgCache;
    }
}
