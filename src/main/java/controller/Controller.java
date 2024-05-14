package controller;


import model.Board;

public class Controller {
    Board board;

    public Controller(Board currentBoard) {
        board = currentBoard;
    }

    public void moveUP() {
        board.moveUp();
    }

    public void moveLeft() {
        board.moveLeft();
    }

    public void moveDown() {
        board.moveDown();
    }

    public void moveRight() {
        board.moveRight();
    }

    public void spawn() {
         board.spawn();
    }

    /**
     * Spawns two Tiles for the start of each game
     */
    public void startSpawn() {
        board.spawn();
        board.spawn();
    }
    public void clearBoard() {
        board.clearBoard();
    }

}
