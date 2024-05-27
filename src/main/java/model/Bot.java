package model;

import controller.Controller;
import javafx.application.Platform;
import view.Game2048;

import java.util.Random;

public class Bot implements Runnable {

    private Board board;
    private Game2048 view;
    private Controller ctrl;
    private Random rnd = new Random();
    public boolean running;

    public Bot(Board b, Game2048 application) {
        board = b;
        view = application;
    }

    @Override
    public void run() {
        System.out.println("run");
        int num = 0;
        while (board.isSpawned() && running) {
            System.out.println("loop");
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            num = rnd.nextInt(4); // Adjusted for proper usage
            Board.Direction value = Board.Direction.values()[num];
            board.move(value);

            // Ensure UI updates are performed on the JavaFX Application Thread
            Platform.runLater(() -> view.updateUI(view.getGridPane(), board));
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
