package com.example.demo;

public class JavaArch {
    public static void main(String[] args) {
        //JAVA判断32位还是64位
        String arch = System.getProperty("sun.arch.data.model");
        System.out.println("arch:" + arch);
    }
}