package controller;

import model.Board;
import model.Tile;
import model.Board.Direction;

public class Controller {
    private Board board;

    public Controller(Board currentBoard) {
        board = currentBoard;
    }

    public void moveUp() {
        board.move(Direction.UP);
    }

    public void moveLeft() {
        board.move(Direction.LEFT);
    }

    public void moveDown() {
        board.move(Direction.DOWN);
    }

    public void moveRight() {
        board.move(Direction.RIGHT);
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
        return board.getTile(x, y);
    }

    public int getNumber(Tile tile) {
        return tile.getNumber();
    }
}
