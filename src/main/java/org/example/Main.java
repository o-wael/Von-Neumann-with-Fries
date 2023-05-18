package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        int[] arr = new int[3];
        final int x = 0;
        arr[0] = x;
        arr[0] = 2;

        System.out.println(arr[0]);

    }
}