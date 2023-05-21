package org.example;

import memory.MainMemory;
import memory.RegisterFile;
import parser.Parser;

import java.io.FileNotFoundException;

public class Main {

    int x;
    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("Hello world!");
//
//        int[] arr = new int[3];
//        final int x = 0;
//        arr[0] = x;
//        arr[0] = 2;
//
//        System.out.println(arr[0]);
//
//        Main mia = new Main();
//        int oldPC = 0b11000000000000000000000000000000;
//        //concatenate bits 31:28 of oldPC with bits 27:0 of address
//        int newPC = (oldPC & 0b11110000000000000000000000000000) | 0b11;
//        System.out.print(Integer.toBinaryString(newPC));

//        System.out.println();

        Parser.readAssemblyFile();
        System.out.println(MainMemory.getMainMemoryInstance().getCurNumOfInstructions());
        for(String s: MainMemory.getMainMemoryInstance().getAssemblyMemory()) {
            if (s != null)
                System.out.println(s);
        }
    }
    
}