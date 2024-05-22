package model;

import java.util.Random;

public class Tile {

    private int boardSize;
    private int number;
    private int x;
    private int y;

    public Tile(int boardSize) {
        number = generateNum();
        setBoardSize(boardSize);
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


    public void setX(int x) {
        if (x >= 0 && x < (boardSize)) {
            this.x = x;
        } else {
            throw new CustomException("X cannot be >5 or <1");
        }
    }

    public void setY(int y) {
        if (y >= 0 && y < boardSize) {
            this.y = y;
        } else {
            throw new CustomException("Y cannot be >5 or <1");
        }
    }

    public void setBoardSize(int boardSize) {
        if(boardSize > 0) {
            this.boardSize = boardSize;
        } else {
            throw new CustomException("Board cannot  be < 1");
        }
    }

    /**
     * Generates a Random Number for each Tile, there is a 90% change that the Number is 2 and a 10% chance for the Number to be 4
     * @return the generated Number which can be 2 or 4
     */
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
