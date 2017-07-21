package com.com.ycj.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * Created by Mtime on 2017/7/17.
 */
public class FileWalk {

    public static void main(String[] args) {

        Path path = Paths.get("D:/");
        try {
            Files.walkFileTree(path, new MyVisitor());
        }catch (Exception e){

        }


    }


    static class MyVisitor extends SimpleFileVisitor<Path> {


        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println(dir.getFileName());
            return super.preVisitDirectory(dir, attrs);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

            System.out.println(file.getFileName());
            return super.visitFile(file, attrs);
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return super.visitFileFailed(file, exc);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return super.postVisitDirectory(dir, exc);
        }
    }
}
