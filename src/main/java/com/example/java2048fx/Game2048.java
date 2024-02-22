package com.example.java2048fx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

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
        Text text;
        board.startSpawn();

        for (int col = 0; col < GRID_SIZE; col++) {
            for (int row = 0; row < GRID_SIZE; row++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.WHITE);
                square.setStroke(Color.BLACK); // Optional: to see square borders
                if (board.getTile(col, row) == null) {
                    text = new Text("  ");
                } else {
                    text = new Text(String.valueOf(board.getTile(col, row).getNumber()));
                }
                StackPane stackPane = new StackPane(square, text);
                gridPane.add(stackPane, col, row);
            }
        }

        StackPane root = new StackPane();
        root.getChildren().add(gridPane);

        Scene sceneMain = new Scene(root);

        sceneMain.setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case W:
                     board.moveUp();
                     updateUI(gridPane,board);
                     break;

                     case A:
                     System.out.println("links");
                     board.moveLeft();
                     updateUI(gridPane,board);
                     break;

                     case S:
                     System.out.println("runter");
                     board.moveDown();
                     updateUI(gridPane,board);
                     break;

                     case D:
                     System.out.println("rechts");
                     board.moveRight();
                     updateUI(gridPane,board);
                     break;

                    default:
                     System.out.println("default");
                     break;
                }
            }
        }
        );
            primaryStage.setScene(sceneMain);
            primaryStage.show();
        }
    public void updateUI(GridPane gridPane, Board board) {

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

                StackPane stackPane = new StackPane(square, text);
                gridPane.add(stackPane, row, col); // Note: row and col are swapped here
            }
        }
    }

    public static void main (String[]args){
            launch(args);
        }
    }
