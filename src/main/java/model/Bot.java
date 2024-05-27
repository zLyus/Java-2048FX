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
                Thread.sleep(100);  // Change sleep time to 100ms
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
                double score = expectimax(clonedBoard, 4, false); // Using depth 4 for expectimax
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = direction;
                }
            }
        }
        return bestMove;
    }

    private boolean simulateMove(Board board, Board.Direction direction) {
        Board clonedBoardBeforeMove = cloneBoard(board);
        board.move(direction);
        return !isBoardEqual(clonedBoardBeforeMove, board);
    }

    private double expectimax(Board board, int depth, boolean isComputerTurn) {
        if (depth == 0 || board.checkSpace() == 0) {
            return evaluateBoard(board);
        }

        if (isComputerTurn) {
            double totalScore = 0;
            int emptyCount = 0;
            for (int i = 0; i < board.getBoardSize(); i++) {
                for (int j = 0; j < board.getBoardSize(); j++) {
                    if (board.getTile(i, j) == null) {
                        emptyCount++;
                        Board clonedBoard = cloneBoard(board);
                        clonedBoard.board[i][j] = new Tile(board.getBoardSize());
                        totalScore += expectimax(clonedBoard, depth - 1, false);
                    }
                }
            }
            return emptyCount == 0 ? 0 : totalScore / emptyCount;
        } else {
            double maxScore = Double.NEGATIVE_INFINITY;
            for (Board.Direction direction : Board.Direction.values()) {
                Board clonedBoard = cloneBoard(board);
                if (simulateMove(clonedBoard, direction)) {
                    maxScore = Math.max(maxScore, expectimax(clonedBoard, depth - 1, true));
                }
            }
            return maxScore;
        }
    }

    private double evaluateBoard(Board board) {
        return getSmoothness(board) + getMonotonicity(board) + getEmptyTiles(board) + getMaxTile(board) + getEdgeTileScore(board) - getIsolatedTilePenalty(board);
    }

    private double getSmoothness(Board board) {
        double smoothness = 0;
        Tile[][] grid = board.board;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    int value = grid[i][j].getNumber();
                    if (i > 0 && grid[i - 1][j] != null) {
                        smoothness -= Math.abs(value - grid[i - 1][j].getNumber());
                    }
                    if (j > 0 && grid[i][j - 1] != null) {
                        smoothness -= Math.abs(value - grid[i][j - 1].getNumber());
                    }
                }
            }
        }

        return smoothness;
    }

    private double getMonotonicity(Board board) {
        double[] totals = {0, 0, 0, 0};
        Tile[][] grid = board.board;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                if (grid[i][j] != null && grid[i][j + 1] != null) {
                    int current = grid[i][j].getNumber();
                    int next = grid[i][j + 1].getNumber();
                    if (current > next) {
                        totals[0] += current - next;
                    } else if (next > current) {
                        totals[1] += next - current;
                    }
                }
            }
        }

        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length - 1; i++) {
                if (grid[i][j] != null && grid[i + 1][j] != null) {
                    int current = grid[i][j].getNumber();
                    int next = grid[i + 1][j].getNumber();
                    if (current > next) {
                        totals[2] += current - next;
                    } else if (next > current) {
                        totals[3] += next - current;
                    }
                }
            }
        }

        return -Math.min(totals[0], totals[1]) - Math.min(totals[2], totals[3]);
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

    private double getMaxTile(Board board) {
        double maxTile = 0;
        Tile[][] grid = board.board;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    maxTile = Math.max(maxTile, grid[i][j].getNumber());
                }
            }
        }
        return maxTile;
    }

    private double getEdgeTileScore(Board board) {
        double score = 0;
        Tile[][] grid = board.board;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    int value = grid[i][j].getNumber();
                    if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[i].length - 1) {
                        score += value;
                    }
                }
            }
        }
        return score;
    }

    private double getIsolatedTilePenalty(Board board) {
        double penalty = 0;
        Tile[][] grid = board.board;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    int value = grid[i][j].getNumber();
                    boolean isolated = true;
                    if (i > 0 && grid[i - 1][j] != null) {
                        isolated = false;
                    }
                    if (i < grid.length - 1 && grid[i + 1][j] != null) {
                        isolated = false;
                    }
                    if (j > 0 && grid[i][j - 1] != null) {
                        isolated = false;
                    }
                    if (j < grid[i].length - 1 && grid[i][j + 1] != null) {
                        isolated = false;
                    }
                    if (isolated) {
                        penalty += value;
                    }
                }
            }
        }
        return penalty;
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

    private boolean isBoardEqual(Board board1, Board board2) {
        for (int i = 0; i < board1.getBoardSize(); i++) {
            for (int j = 0; j < board1.getBoardSize(); j++) {
                Tile tile1 = board1.getTile(i, j);
                Tile tile2 = board2.getTile(i, j);
                if ((tile1 == null && tile2 != null) || (tile1 != null && tile2 == null)) {
                    return false;
                }
                if (tile1 != null && tile2 != null && tile1.getNumber() != tile2.getNumber()) {
                    return false;
                }
            }
        }
        return true;
    }
}