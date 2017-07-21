package com.com.ycj.console;

import java.util.Scanner;

public class Console {


    public static void main(String[] args) {

        readConsoleByScanner();
    }


    public static void readConsoleByScanner() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input string:");
        String content ="";
        while(scanner.hasNext()&&!(content=scanner.nextLine()).equals("end"))
        System.out.println(content);

    }


    public static void readConsole(){


    }


}
