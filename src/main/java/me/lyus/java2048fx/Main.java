package me.lyus.java2048fx;

public class Main {

    public static void main(String[] args) {
        Board brett = new Board();
        brett.print();
        brett.spawn();
        brett.spawn();
        System.out.println("---------------- nach oben");
        brett.moveUp();
        brett.print();
        System.out.println("---------------- nach links");
        brett.moveLeft();
        brett.print();
        System.out.println("---------------- nach rechts");
        brett.moveRight();
        brett.print();
        System.out.println("---------------- nach unten");
        brett.moveDown();
        brett.print();
        System.out.println("----------------");
    }
}