package com.example.java2048fx;

import java.util.Random;

public class Board {
    private Tile[][] board;

    public Board() {
        board = new Tile[4][4];
    } // 1.) x 2.) y


    public void startSpawn() {
        Random rnd = new Random();
        Tile t1 = new Tile();
        t1.setX(rnd.nextInt(board.length));
        t1.setY(rnd.nextInt(board.length));
        Tile t2 = new Tile();
        t2.setX(rnd.nextInt(board.length));
        t2.setY(rnd.nextInt(board.length));
        while (t2.getX() == t1.getX() && t2.getY() == t1.getY()) {
            t2.setX(rnd.nextInt(board.length));
        }

        board[t1.getY()][t1.getX()] = t1;
        board[t2.getY()][t2.getX()] = t2;
    }

    public synchronized void spawn() {
        if (checkIfSpace()) {
            Random rnd = new Random();
            Tile t3 = new Tile();
            int boardWidth = board.length;
            int boardHeight = board[0].length; // Assuming the board is a square or rectangular  2D array

            do {
                t3.setX(rnd.nextInt(boardWidth));
                t3.setY(rnd.nextInt(boardHeight));
            } while (board[t3.getY()][t3.getX()] != null);

            board[t3.getY()][t3.getX()] = t3;
        }
    } // verloren

    public boolean checkIfSpace() {
        for (int col = 0; col < board.length; col++) { //y
            int count = 0;
            for (int row = 0; row < board[col].length; row++) { //x
                if(board[col][row] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveUp() {
        for (int col = 1; col < board.length; col++) {
            for (int row = 0; row < board[col].length; row++) {
                if (board[col][row] != null) {
                    int currentRow = col;
                    while (currentRow > 0 && board[currentRow - 1][row] == null) {

                        board[currentRow - 1][row] = board[currentRow][row];
                        board[currentRow][row] = null;
                        board[currentRow - 1][row].setY(currentRow - 1);
                        currentRow--;
                    }
                }
            }
        }
        spawn();
        merge(Direction.UP);
    }



    public void moveDown() {
        for (int col = board.length - 2; col >= 0; col--) {
            for (int row = 0; row < board[col].length; row++) {
                if (board[col][row] != null) {
                    int currentRow = col;
                    while (currentRow < board.length - 1 && board[currentRow + 1][row] == null) {

                        board[currentRow + 1][row] = board[currentRow][row];
                        board[currentRow][row] = null;
                        board[currentRow + 1][row].setY(currentRow + 1);
                        currentRow++;
                    }
                }
            }
        }
        spawn();
        merge(Direction.DOWN);
    }

    public void moveLeft() {
        for (int row = 1; row < board[0].length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[col][row] != null) {
                    int currentCol = row;
                    while (currentCol > 0 && board[col][currentCol - 1] == null) {

                        board[col][currentCol - 1] = board[col][currentCol];
                        board[col][currentCol] = null;
                        board[col][currentCol - 1].setX(currentCol - 1);
                        currentCol--;
                    }
                }
            }
        }
        spawn();
        merge(Direction.LEFT);
    }

    public void moveRight() {
        for (int row = board[0].length - 2; row >= 0; row--) {
            for (int col = 0; col < board.length; col++) {
                if (board[col][row] != null) {
                    int currentCol = row;
                    while (currentCol < board[0].length - 1 && board[col][currentCol + 1] == null) {

                        board[col][currentCol + 1] = board[col][currentCol];
                        board[col][currentCol] = null;
                        board[col][currentCol + 1].setX(currentCol + 1);
                        currentCol++;
                    }
                }
            }
        }
        spawn();
        merge(Direction.RIGHT);
    }

    public void merge(Direction direction) {
        int mergeDirection = 0;
        switch (direction) {
            case UP:
                for (int col = 0; col < board.length; col++) {
                    for (int row = 0; row < board.length; row++) {
                        if ((col - 1) >= 0 && board[col][row] != null && board[col - 1][row] != null) {
                            if (board[col][row].getNumber() == board[col - 1][row].getNumber()) {
                                board[col][row] = null;
                                board[col - 1][row].setNumber(board[col - 1][row].getNumber() * 2);
                            }
                        }
                    }
                }
                break;

            case DOWN:
                for (int col = 0; col < board.length; col++) {
                    for (int row = 0; row < board.length; row++) {
                        if ((col + 1) <= 3 && board[col][row] != null && board[col + 1][row] != null) {
                            if (board[col][row].getNumber() == board[col + 1][row].getNumber()) {
                                board[col][row] = null;
                                board[col + 1][row].setNumber(board[col + 1][row].getNumber() * 2);
                            }
                        }
                    }
                }
                break;

            case LEFT:
                for (int col = 0; col < board.length; col++) {
                    for (int row = 0; row < board.length; row++) {
                        if ((row - 1) >= 0 && board[col][row] != null && board[col][row - 1] != null) {
                            if (board[col][row].getNumber() == board[col][row - 1].getNumber()) {
                                board[col][row] = null;
                                board[col][row - 1].setNumber(board[col][row - 1].getNumber() * 2);
                            }
                        }
                    }
                }
                break;

            case RIGHT:
                for (int col = 0; col < board.length; col++) {
                    for (int row = 0; row < board.length; row++) {
                        if ((row + 1) <= 3 && board[col][row] != null && board[col][row + 1] != null) {
                            if (board[col][row].getNumber() == board[col][row + 1].getNumber()) {
                                board[col][row] = null;
                                board[col][row + 1].setNumber(board[col][row + 1].getNumber() * 2);
                            }
                        }
                    }
                }
                break;

        }
    }
    public void print() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] != null) {
                    System.out.print(board[i][j].getNumber());
                } else {
                    System.out.print("0");
                }
                System.out.print("  ");
            }
            System.out.println();
        }
    }


    public Tile getTile(int y, int x) {
        return board[y][x];
    }
}