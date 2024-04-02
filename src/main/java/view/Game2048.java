
package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Board;

public class Game2048 extends Application {
    public static final int GRID_SIZE = 4;
    public static final int SQUARE_SIZE = 100; // Adjust this value to change square size

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2048Game");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        Board board = new Board();
        UI ui = new UI();
        StackPane root = new StackPane();
        root.getChildren().add(gridPane);

        Scene sceneMain = new Scene(root);

        primaryStage.setScene(sceneMain);
        primaryStage.show();

        board.spawn();
        board.spawn();
        ui.updateUI(gridPane, board);

        sceneMain.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    board.moveUp();
                    ui.updateUI(gridPane, board);
                    board.print();
                    break;
                case S:
                    board.moveDown();
                    ui.updateUI(gridPane, board);
                    board.print();
                    break;
                case A:
                    board.moveLeft();
                    ui.updateUI(gridPane, board);
                    board.print();
                    break;
                case D:
                    board.moveRight();
                    ui.updateUI(gridPane, board);
                    board.print();
                    break;
                default:
                    break;
            }
        });
    }
}
