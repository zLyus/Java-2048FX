package me.lyus.java2048fx;

import java.util.Random;

public class Board {
    private Tile[][] board;

    public Board() {
        board = new Tile[4][4];
    } // 1.) x 2.) y


    public synchronized void spawn() {
        if (checkIfSpace() > 0) {
            Random rnd = new Random();
            Tile t3 = new Tile();
            int boardWidth = board.length;
            int boardHeight = board[0].length; // Assuming the board is a square or rectangular  2D array

            do {
                t3.setX(rnd.nextInt(boardWidth));
                t3.setY(rnd.nextInt(boardHeight));
            } while (board[t3.getY()][t3.getX()] != null);

            board[t3.getY()][t3.getX()] = t3;
        } else {
            throw new CustomException("verloren");
        }
    }

    public int checkIfSpace() {
        int count = 0;
        for (int col = 0; col < board.length; col++) { //y
            for (int row = 0; row < board[col].length; row++) { //x
                if(board[col][row] == null) {
                    count++;
                }
            }
        }
        return count;
    }

    public void moveUp() {
        boolean alreadyMerged = false;
        for (int row = 0; row < board[0].length; row++) {
            for (int col = 1; col < board.length; col++) {
                if (board[col][row] != null) {
                    int currentCol = col;
                    while (board[currentCol][row] != null && currentCol > 0 && (board[currentCol - 1][row] == null ||
                            board[currentCol - 1][row].getNumber() == board[currentCol][row].getNumber())) {
                        if(alreadyMerged) {
                            currentCol = col;
                            alreadyMerged = false;
                        }
                        if (board[currentCol][row] != null && board[currentCol - 1][row] == null) {
                            board[currentCol - 1][row] = board[currentCol][row];
                            board[currentCol][row] = null;
                            board[currentCol - 1][row].setY(currentCol - 1);
                        } else if (board[currentCol][row] != null && board[currentCol - 1][row].getNumber() == board[currentCol][row].getNumber()) {
                            board[currentCol - 1][row].setNumber(board[currentCol - 1][row].getNumber() * 2);
                            board[currentCol][row] = null;
                            alreadyMerged = true;
                        }

                        currentCol--;
                    }
                }
            }
        }
        spawn();
    }



    public void moveDown() {
        boolean alreadyMerged = false;
        for (int row = 0; row < board[0].length; row++) {
            for (int col = board.length - 2; col >= 0; col--) {
                if (board[col][row] != null) {
                    int currentCol = col;
                    while (board[currentCol][row] != null && currentCol < board.length - 1 && (board[currentCol + 1][row] == null ||
                            board[currentCol + 1][row].getNumber() == board[currentCol][row].getNumber())) {
                        if(alreadyMerged) {
                            currentCol = col;
                            alreadyMerged = false;
                        }
                        if (board[currentCol + 1][row] == null && board[currentCol][row] != null) {
                            board[currentCol + 1][row] = board[currentCol][row];
                            board[currentCol][row] = null;
                            board[currentCol + 1][row].setY(currentCol + 1);
                        } else if (board[currentCol][row] != null && board[currentCol + 1][row].getNumber() == board[currentCol][row].getNumber()) {
                            board[currentCol + 1][row].setNumber(board[currentCol + 1][row].getNumber() * 2);
                            board[currentCol][row] = null;
                            alreadyMerged = true;
                        }

                        currentCol++;
                    }
                }
            }
        }
        spawn();
    }

    public void moveLeft() {
        boolean alreadyMerged = false;
        for (int col = 0; col < board.length; col++) {
            for (int row = 1; row < board[0].length; row++) {
                if (board[col][row] != null) {
                    int currentRow = row;
                    while (board[col][currentRow] != null && currentRow > 0 && (board[col][currentRow - 1] == null ||
                            board[col][currentRow - 1].getNumber() == board[col][currentRow].getNumber())) {
                        if(alreadyMerged) {
                            currentRow = row;
                            alreadyMerged = false;
                        }
                        if (board[col][currentRow - 1] == null && board[col][currentRow] != null) {
                            board[col][currentRow - 1] = board[col][currentRow];
                            board[col][currentRow] = null;
                            board[col][currentRow - 1].setX(currentRow - 1);
                        } else if (board[col][currentRow] != null && board[col][currentRow - 1].getNumber() == board[col][currentRow].getNumber()) {
                            board[col][currentRow - 1].setNumber(board[col][currentRow - 1].getNumber() * 2);
                            board[col][currentRow] = null;
                            alreadyMerged = true;
                        }

                        currentRow--;
                    }
                }
            }
        }
        spawn();
    }

    public void moveRight() {
        boolean alreadyMerged = false;
        for (int col = 0; col < board.length; col++) {
            for (int row = board[0].length - 2; row >= 0; row--) {
                if (board[col][row] != null) {
                    int currentRow = row;
                    while (board[col][currentRow] != null && currentRow < board[0].length - 1 && (board[col][currentRow + 1] == null ||
                            board[col][currentRow + 1].getNumber() == board[col][currentRow].getNumber())) {
                        if(alreadyMerged) {
                            currentRow = row;
                            alreadyMerged = false;
                        }
                        if (board[col][currentRow + 1] == null && board[col][currentRow] != null) {
                            board[col][currentRow + 1] = board[col][currentRow];
                            board[col][currentRow] = null;
                            board[col][currentRow + 1].setX(currentRow + 1);
                        } else if (board[col][currentRow] != null && board[col][currentRow + 1].getNumber() == board[col][currentRow].getNumber()) {
                            board[col][currentRow + 1].setNumber(board[col][currentRow + 1].getNumber() * 2);
                            board[col][currentRow] = null;
                            alreadyMerged = true;
                        }

                        currentRow++;
                    }
                }
            }
        }
        spawn();
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
        System.out.println("-------------------------");
    }


    public Tile getTile(int y, int x) {
        return board[y][x];
    }
}