package model;

import java.util.Random;

public class Tile {

    private int number;
    private String color;
    private int x;
    private int y;
    private int previousX;
    private int previousY;

    public Tile() {
        number = generateNum();
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number){
        this.number=number;

    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPreviousX() {
        return previousX;
    }

    public void setPreviousX(int previousX) {
        this.previousX = previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void setPreviousY(int previousY) {
        this.previousY = previousY;
    }

    public void setX(int x) {
        if (x >= 0 && x < 4) {
            this.x = x;
        } else {
            throw new CustomException("X cannot be >5 or <1");
        }
    }

    public void setY(int y) {
        if (y >= 0 && y < 4) {
            this.y = y;
        } else {
            throw new CustomException("Y cannot be >5 or <1");
        }
    }

    public int generateNum() {
        int totalProbability = 100;

        Random random = new Random();
        int randomValue = random.nextInt(totalProbability) + 1;

        int result;
        if (randomValue <= totalProbability * 0.1) { //10%
            number = 4;
        } else { //90%
            number = 2;
        }
        return number;
    }



}
