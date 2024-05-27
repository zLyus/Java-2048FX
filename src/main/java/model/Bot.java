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
        int num = 0;
        while (board.isSpawned() && running) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            num = rnd.nextInt(4); // Adjusted for proper usage
            Board.Direction value = Board.Direction.values()[num];
            board.move(value);

            // Ensure UI updates are performed on the JavaFX Application Thread

            Platform.runLater(() -> view.setBoard(board));

            Platform.runLater(() -> view.updateUI(view.getGridPane(), board));
        }
    }
}
