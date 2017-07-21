package com.com.ycj.nio;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by Mtime on 2017/7/18.
 */
public class FileReadWrite {
    public static void main(String[] args) {
        try {
            readFile("D:\\etc\\global.conf");

        } catch (Exception e) {

        }


    }


    public static void readFile(String fileName) throws IOException {
        Objects.requireNonNull(fileName);
        Path path = Paths.get(fileName);

        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path);) {


            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = seekableByteChannel.read(byteBuffer);
            while (count != -1) {
                byteBuffer.rewind();
                for (int i = 0; i < count; i++) {
                    System.out.println(byteBuffer.getChar(i));
                }
                byteBuffer.rewind();
                count = seekableByteChannel.read(byteBuffer);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
