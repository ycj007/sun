package com.com.ycj.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by Mtime on 2017/7/18.
 */
public class Server {
    /*接受数据缓冲区*/
    private static ByteBuffer sBuffer = ByteBuffer.allocate(1024);
    /*发送数据缓冲区*/
    private static ByteBuffer rBuffer = ByteBuffer.allocate(1024);
    //解码buffer
    private static Charset cs = Charset.forName("gbk");

    public static void main(String[] args) {

        Selector selector = getSelector();
        ServerSocketChannel serverSocketChannel =getServerSocketChannel();
        try {
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            listen(selector);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }




    }


    public static ServerSocketChannel getServerSocketChannel(){
        SocketAddress  socketAddress = new InetSocketAddress("127.0.0.1", 8080);
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = (ServerSocketChannel)ServerSocketChannel.open().bind(socketAddress).configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocketChannel;
    }

    public static Selector getSelector(){
        try {
            return Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void listen(Selector selector){
        while (true) {
            try {
                selector.select();//返回值为本次触发的事件数
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for(SelectionKey key : selectionKeys){
                    handle(key,selector);
                }
                selectionKeys.clear();//清除处理过的事件
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

        }
    }

    /**
     * 处理不同的事件
     */
    public static void handle(SelectionKey selectionKey,Selector selector) throws IOException {
        ServerSocketChannel server = null;
        SocketChannel client = null;
        String receiveText=null;
        int count=0;
        if (selectionKey.isAcceptable()) {
            /*
             * 客户端请求连接事件
             * serversocket为该客户端建立socket连接，将此socket注册READ事件，监听客户端输入
             * READ事件：当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件
             */
            server = (ServerSocketChannel) selectionKey.channel();
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            /*
             * READ事件，收到客户端发送数据，读取数据后继续注册监听客户端
             */
            client = (SocketChannel) selectionKey.channel();
            rBuffer.clear();
            count = client.read(rBuffer);
            if (count > 0) {
                rBuffer.flip();
                receiveText = String.valueOf(cs.decode(rBuffer).array());
                System.out.println(client.toString()+":"+receiveText);
                //dispatch(client, receiveText);
                client = (SocketChannel) selectionKey.channel();
                client.register(selector, SelectionKey.OP_READ);
            }
        }
    }


}
