package model;

import javafx.application.Platform;
import view.Game2048;

import java.util.Random;

public class Bot implements Runnable {

    private Board board;
    private Game2048 view;
    private Random rnd = new Random();
    public boolean running;

    public Bot(Board b, Game2048 application) {
        board = b;
        board.print();
        view = application;
    }

    @Override
    public void run() {
        while (board.isSpawned() && running) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Board.Direction bestMove = findBestMove();
            if (bestMove != null) {
                board.move(bestMove);

                Platform.runLater(() -> view.setBoard(board));
                Platform.runLater(() -> view.updateUI(view.getGridPane(), board));
            }

            if (board.checkSpace() == 0) {
                running = false;
                Platform.runLater(() -> view.gameLost());
            }
        }
    }

    private Board.Direction findBestMove() {
        Board.Direction[] directions = Board.Direction.values();
        Board.Direction bestMove = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (Board.Direction direction : directions) {
            Board clonedBoard = cloneBoard(board); // Clone the board to simulate the move
            if (simulateMove(clonedBoard, direction)) {
                double score = evaluateBoard(clonedBoard);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = direction;
                }
            }
        }
        return bestMove;
    }

    private boolean simulateMove(Board board, Board.Direction direction) {
        boolean moved = false;
        board.move(direction); // Move modifies the board
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (board.getTile(i, j) != null) {
                    moved = true;
                    break;
                }
            }
        }
        return moved;
    }

    private double evaluateBoard(Board board) {
        return getMonotonicity(board) + getEmptyTiles(board) + getPotentialMerges(board);
    }

    private double getMonotonicity(Board board) {
        double score = 0;
        Tile[][] grid = board.board;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                if (grid[i][j] != null && grid[i][j + 1] != null) {
                    score += Math.abs(grid[i][j].getNumber() - grid[i][j + 1].getNumber());
                }
            }
        }

        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length - 1; i++) {
                if (grid[i][j] != null && grid[i + 1][j] != null) {
                    score += Math.abs(grid[i][j].getNumber() - grid[i + 1][j].getNumber());
                }
            }
        }

        return -score; // Lower score indicates better monotonicity
    }

    private double getEmptyTiles(Board board) {
        double emptyTiles = 0;
        Tile[][] grid = board.board;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == null) {
                    emptyTiles++;
                }
            }
        }
        return emptyTiles;
    }

    private double getPotentialMerges(Board board) {
        double merges = 0;
        Tile[][] grid = board.board;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                if (grid[i][j] != null && grid[i][j + 1] != null &&
                        grid[i][j].getNumber() == grid[i][j + 1].getNumber()) {
                    merges++;
                }
            }
        }

        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length - 1; i++) {
                if (grid[i][j] != null && grid[i + 1][j] != null &&
                        grid[i][j].getNumber() == grid[i + 1][j].getNumber()) {
                    merges++;
                }
            }
        }

        return merges;
    }

    private Board cloneBoard(Board original) {
        Board clone = new Board(original.getBoardSize());
        for (int i = 0; i < original.getBoardSize(); i++) {
            for (int j = 0; j < original.getBoardSize(); j++) {
                if (original.getTile(i, j) != null) {
                    clone.board[i][j] = new Tile(original.getBoardSize());
                    clone.board[i][j].setNumber(original.getTile(i, j).getNumber());
                    clone.board[i][j].setX(original.getTile(i, j).getX());
                    clone.board[i][j].setY(original.getTile(i, j).getY());
                }
            }
        }
        return clone;
    }
}