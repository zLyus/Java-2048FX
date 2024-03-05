
package me.lyus.java2048fx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game2048 extends Application {

    private static final int GRID_SIZE = 4;
    private static final int SQUARE_SIZE = 100; // Adjust this value to change square size

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2048Game");

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true); // Optional: to see grid lines
        gridPane.setAlignment(Pos.CENTER); // Center align the grid

        Board board = new Board();

        StackPane root = new StackPane();
        root.getChildren().add(gridPane);

        Scene sceneMain = new Scene(root);

        sceneMain.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    board.moveUp();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                case S:
                    board.moveDown();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                case A:
                    board.moveLeft();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                case D:
                    board.moveRight();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                default:
                    break;
            }
        });

        primaryStage.setScene(sceneMain);
        primaryStage.show();

        // Spawn initial tiles after the UI setup
        board.spawn();
        board.spawn();

        // Update the UI initially
        updateUI(gridPane, board);
    }

    public void updateUI(GridPane gridPane, Board board) {
        gridPane.getChildren().clear(); // Clear the GridPane before updating

        for (int col = 0; col < GRID_SIZE; col++) {
            for (int row = 0; row < GRID_SIZE; row++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.WHITE);
                square.setStroke(Color.BLACK); // Optional: to see square borders

                Tile currentTile = board.getTile(col, row);
                Text text;
                if (currentTile != null) {
                    text = new Text(String.valueOf(currentTile.getNumber()));
                } else {
                    text = new Text("  ");
                }
                text.setFont(Font.font("Arial", FontWeight.BOLD, 20));

                StackPane stackPane = new StackPane(square, text);
                GridPane.setRowIndex(stackPane, col);
                GridPane.setColumnIndex(stackPane, row);
                gridPane.getChildren().add(stackPane);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
