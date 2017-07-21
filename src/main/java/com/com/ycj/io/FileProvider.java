package com.com.ycj.io;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Mtime on 2017/7/17.
 */
public class FileProvider {

    private static File in = new File("D://in.txt");
    private static File out = new File("D://out.txt");

    public static File getInFile() {
        if (!in.exists()) {
            try {
                in.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in;
    }

    public static File getOutFile() {
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            out.delete();
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }


    public static void printContent(File file, int flag) {
        Objects.requireNonNull(file, "file not allowed null");

        try (
                InputStream inputStream = new FileInputStream(file);


        ) {
            if (flag == 1) {
                printByInputRead1(inputStream);
            } else if (flag == 2) {
                printByInputRead2(inputStream);
            } else {
                printByBufferedInputRead1(inputStream);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static void printByInputRead1(InputStream inputStream) throws IOException {

        byte c = (byte) inputStream.read();
        int size = inputStream.available();
        byte[] bytes = new byte[size];
        int index = 0;
        while (c != -1) {
            bytes[index] = c;
            index++;
            c = (byte) inputStream.read();
            if (index >= size) {
                byte[] newBytes = new byte[size * 2];
                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                bytes = newBytes;
            }
        }
        System.out.println(new String(bytes));


    }

    private static void printByInputRead2(InputStream inputStream) throws IOException {
        int size = inputStream.available();
        byte[] bytes = new byte[size];
        inputStream.read(bytes);
        System.out.println(new String(bytes));
    }

    private static void printByBufferedInputRead1(InputStream inputStream) throws IOException {
        BufferedInputStream bf = new BufferedInputStream(inputStream);
        int size = inputStream.available();
        byte[] bytes = new byte[size];
        bf.read(bytes);
        System.out.println(new String(bytes));
    }


    public static void fileCopy1(File in, File out) throws IOException {


        try (

                FileInputStream fileInputStream = new FileInputStream(in);
                FileOutputStream outputStream = new FileOutputStream(out);

        ) {
            byte[] buf = new byte[8];
            int index =0;
            while ((index = fileInputStream.read(buf)) != -1) {
                outputStream.write(buf,0,index);
                outputStream.flush();
            }
        }


    }

    public static void fileCopy2(File in, File out) throws IOException {

        try (
                FileInputStream fileInputStream = new FileInputStream(in);
                FileOutputStream outputStream = new FileOutputStream(out);
        ) {
            int c = 0;
            while ((c = fileInputStream.read()) != -1) {
                outputStream.write(c);
                //outputStream.flush();
            }
        }


    }


    public static void fileCopy3(File in, File out) throws IOException {

        try (
                FileInputStream fileInputStream = new FileInputStream(in);
                FileOutputStream outputStream = new FileOutputStream(out);

                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ) {
            byte[] buf = new byte[8];
            int index = 0;
            while ((index = bufferedInputStream.read(buf) )!= -1) {
                bufferedOutputStream.write(buf,0,index);
                bufferedOutputStream.flush();

            }
        }
    }

    public static void fileCopy4(File in, File out) throws IOException {

        try (
                FileInputStream fileInputStream = new FileInputStream(in);
                FileOutputStream outputStream = new FileOutputStream(out);


                InputStreamReader reader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        ) {
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            String c = null;
            while ((c = bufferedReader.readLine()) != null) {
                writer.write(c);
                writer.flush();
                writer.newLine();
            }

        }
    }


}
