package controller;

import model.Board;
import model.Tile;
import model.Board.Direction;

public class Controller {
    private Board board;

    public Controller(Board currentBoard) {
        board = currentBoard;
    }

    public void move(Direction direction) {
        board.move(direction);
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

    public int getHighestNumber() {return board.getHighestNumber();}
}
