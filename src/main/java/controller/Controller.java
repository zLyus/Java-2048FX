package controller;


import model.Board;
import model.Tile;

public class Controller {
    Board board;

    public Controller(Board currentBoard) {
        board = currentBoard;
    }

    public void moveUp() {
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

    public Board getBoard() {
        return board;
    }

    public boolean isSpawned() {
        return board.isSpawned();
    }

    public Tile getTile(int x, int y) {
        return board.getTile(x,y);
    }

    public int getNumber(Tile tile) {
        return tile.getNumber();
    }




}
