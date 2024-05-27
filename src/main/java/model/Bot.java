package model;

import javafx.application.Platform;
import view.Game2048;

import java.util.Random;

public class Bot implements Runnable {

    private Board board;
    private Game2048 view;
    private Random rnd = new Random();
    public boolean running;
    public boolean lost;

    public Bot(Board b, Game2048 application) {
        board = b;
        board.print();
        view = application;
    }

    @Override
    public void run() {
        int num;
        while (board.isSpawned() && running) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            num = rnd.nextInt(4);
            Board.Direction value = Board.Direction.values()[num];
            board.move(value);

            Platform.runLater(() -> view.setBoard(board));

            Platform.runLater(() -> view.updateUI(view.getGridPane(), board));
            if(!(board.checkSpace() > 0)) {
                running = false;
                 Platform.runLater(() -> view.gameLost());
            }
        }
    }
}
