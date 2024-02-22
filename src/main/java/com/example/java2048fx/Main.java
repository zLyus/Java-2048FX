package com.example.java2048fx;

public class Main {

    public static void main(String[] args) {
        Board brett = new Board();
        brett.startSpawn();
        brett.print();
        System.out.println("----------------");
    }
}