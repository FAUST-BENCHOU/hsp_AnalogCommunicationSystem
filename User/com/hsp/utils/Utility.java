package com.hsp.utils;

import java.util.Scanner;

public class Utility {
    private static Scanner sc = null;
    static {
        sc = new Scanner(System.in);
    }
    public static String readString(){
        String next = sc.nextLine();
        return next;
    }

}