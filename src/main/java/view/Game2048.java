package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Board;
import model.Tile;

public class Game2048 extends Application {

    public static final int GRID_SIZE = 4;
    public static final int SQUARE_SIZE = 100;

    private GridPane gridPane = new GridPane();
    private Scene gameScene = new Scene(gridPane, 800,600);
    private StackPane stackPane = new StackPane();
    private Stage endStage = new Stage();
    private Scene endScene = new Scene(stackPane, 400, 200);
    private Button resetButton = new Button("Reset");
    private Board board = new Board();
    Controller controller = new Controller();

    @Override
    public void start(Stage gameStage) {

        stackPane.getChildren().add(resetButton);

        board.clearBoard();
        controller.spawn(board);
        controller.spawn(board);
        updateUI(gridPane, board);

        gridPane.setAlignment(Pos.CENTER);

        updateUI(gridPane, board);

        resetButton.setOnAction(new ButtonEvent);

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                case W:
                    board.moveUp();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                    case DOWN:
                case S:
                    board.moveDown();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                    case LEFT:
                case A:
                    board.moveLeft();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                    case RIGHT:
                case D:
                    board.moveRight();
                    updateUI(gridPane, board);
                    board.print();
                    break;
                default:
                    break;
            }
        });
        gameStage.setTitle("2048Game");
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    public void gameEnded() {
        endStage.show();
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
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 20));

                } else {
                    text = new Text(" ");
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                }

                StackPane stackPane = new StackPane(square, text);
                GridPane.setRowIndex(stackPane, col);
                GridPane.setColumnIndex(stackPane, row);
                gridPane.getChildren().add(stackPane);
            }
        }


    }


}
