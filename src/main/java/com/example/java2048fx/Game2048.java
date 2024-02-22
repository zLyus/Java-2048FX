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

import java.util.EventListener;

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

        sceneMain.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case W:
                     System.out.println("oben");
                     board.moveUp();
                     break;
                     case A:
                         System.out.println("links");
                         board.moveLeft();
                     break;
                     case S:
                     System.out.println("runter");
                     board.moveDown();
                     break;
                     case D:
                     System.out.println("rechts");
                     board.moveRight();
                     break;
                    default:
                     System.out.println("default");
                     break;
                }
                Text updateText;
                for (int updateCol = 0; updateCol < GRID_SIZE; updateCol++) {
                    for (int updateRow = 0; updateRow < GRID_SIZE; updateRow++) {
                        Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.WHITE);
                        square.setStroke(Color.BLACK); // Optional: to see square borders
                        if (board.getTile(updateCol, updateRow) == null) {
                            updateText = new Text("  ");
                        } else {
                            updateText = new Text(String.valueOf(board.getTile(updateCol, updateRow).getNumber()));
                        }
                        StackPane stackPane = new StackPane(square, updateText);
                        gridPane.add(stackPane, updateCol, updateRow);
                    }
                }
            }
        }
        );
            primaryStage.setScene(sceneMain);
            primaryStage.show();
        }
        public static void main (String[]args){
            launch(args);
        }
    }
