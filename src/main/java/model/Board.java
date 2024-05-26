package model;

import java.io.Serializable;
import java.util.Random;

public class Board implements Serializable {
    public Tile[][] board;
    private boolean spawned;

    public Board(int size) {
        board = new Tile[size][size];
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Generates Coordinates dependent on boardsize, checks if the slot for the coordinates is empty, if no it repeats
     */
    public void spawn() {
        if (checkSpace() > 0) {
            Random rnd = new Random();
            Tile t3 = new Tile(board.length);
            int boardWidth = board.length;
            int boardHeight = board.length;

            do {
                t3.setX(rnd.nextInt(boardWidth));
                t3.setY(rnd.nextInt(boardHeight));
            } while (board[t3.getY()][t3.getX()] != null);

            board[t3.getY()][t3.getX()] = t3;
            spawned = true;
        } else {
            spawned = false;
        }
    }

    /**
     * @return highest Number
     */
    public int getHighestNumber() {
        int highest = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (highest < board[i][j].getNumber()) {
                        highest = board[i][j].getNumber();
                    }
                }
            }
        }
        return highest;
    }

    public boolean isSpawned() {
        return spawned;
    }

    /**
     * @return the amount of empty slots in the board
     */
    public int checkSpace() {
        int count = 0;
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board[col].length; row++) {
                if (board[col][row] == null) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getBoardSize() {
        return board.length;
    }

    /**
     * Moves the tiles in the specified direction.
     *
     * @param direction the direction to move the tiles
     */
    public void move(Direction direction) {
        boolean moved;
        do {
            moved = false;
            for (int col = 0; col < board.length; col++) {
                for (int row = 0; row < board[col].length; row++) {
                    if (board[col][row] != null) {
                        int nextCol = col;
                        int nextRow = row;

                        while (true) {
                            int newCol = nextCol;
                            int newRow = nextRow;

                            switch (direction) {
                                case UP:
                                    newCol = nextCol - 1;
                                    break;
                                case DOWN:
                                    newCol = nextCol + 1;
                                    break;
                                case LEFT:
                                    newRow = nextRow - 1;
                                    break;
                                case RIGHT:
                                    newRow = nextRow + 1;
                                    break;
                            }

                            if (newCol < 0 || newCol >= board.length || newRow < 0 || newRow >= board[nextCol].length) {
                                break;
                            }

                            if (board[newCol][newRow] == null) {
                                board[newCol][newRow] = board[nextCol][nextRow];
                                board[nextCol][nextRow] = null;
                                board[newCol][newRow].setX(newRow);
                                board[newCol][newRow].setY(newCol);
                                moved = true;
                            } else if (board[newCol][newRow].getNumber() == board[nextCol][nextRow].getNumber()) {
                                board[newCol][newRow].setNumber(board[newCol][newRow].getNumber() * 2);
                                board[nextCol][nextRow] = null;
                                moved = true;
                                break;
                            } else {
                                break;
                            }

                            nextCol = newCol;
                            nextRow = newRow;
                        }
                    }
                }
            }
        } while (moved);
        spawn();
    }

    /**
     * Empty the board, which sets every value to NULL
     */
    public void clearBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * @param y - Y coordinate
     * @param x - X coordinate
     * @return Tile from the coordinates
     */
    public Tile getTile(int y, int x) {
        return board[y][x];
    }
}
