package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Board;
import model.Tile;

import java.util.Objects;

public class Game2048 extends Application {

    public static final int GRID_SIZE = 4;
    public static final int SQUARE_SIZE = 100;

    private GridPane gridPane = new GridPane();

    private GridPane gameLost = new GridPane();
    private Stage endStage = new Stage();
    private Button resetButton = new Button("Reset");
    private Board board = new Board();
    Controller controller = new Controller();
    Label currentHighScore = new Label("Highest Number");
    VBox vbox = new VBox();
    Image image = new Image("/gameBoard.png");
    ImageView backGround = new ImageView(image);

    @Override
    public void start(Stage gameStage) {

        backGround.setImage(backGround.getImage());
        backGround.setFitHeight(400);
        backGround.setFitWidth(400);
        vbox.getChildren().add(backGround);

        board.clearBoard();
        board.spawn();
        board.spawn();
        updateUI(gridPane, board);
        vbox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

        updateUI(gridPane, board);

        vbox.getChildren().add(currentHighScore);
        vbox.getChildren().add(gridPane);

        Scene gameScene = new Scene(vbox, 800,600);

        gameStage.setTitle("2048Game");
        gameStage.setScene(gameScene);
        gameStage.show();


        resetButton.setOnAction(event -> {
            board.clearBoard();
            board.spawn();
            board.spawn();
            updateUI(gridPane,board);
            endStage.hide();
        });

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                case W:
                    board.moveUp();
                    updateUI(gridPane, board);
                    board.print();
                    if (!board.isSpawned()) {
                        gameEnded();
                    }
                    break;
                    case DOWN:
                case S:
                    board.moveDown();
                    updateUI(gridPane, board);
                    board.print();
                    if (!board.isSpawned()) {
                        gameEnded();
                    }
                    break;
                    case LEFT:
                case A:
                    board.moveLeft();
                    updateUI(gridPane, board);
                    board.print();
                    if (!board.isSpawned()) {
                        gameEnded();
                    }
                    break;
                    case RIGHT:
                case D:
                    board.moveRight();
                    updateUI(gridPane, board);
                    board.print();
                    if (!board.isSpawned()) {
                        gameEnded();
                    }
                    break;
                default:
                    break;
            }
        });
    }


    public void gameEnded() {
        gameLost.add(resetButton,0,0);
        Scene endScene = new Scene(gameLost, 400, 200);
        endStage.setScene(endScene);
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
