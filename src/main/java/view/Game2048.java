package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import model.FileManager;
import model.Tile;


public class Game2048 extends Application {

    public static final int GRID_SIZE = 4;
    public static final int SQUARE_SIZE = 100;

    private GridPane gridPane = new GridPane();
    FileManager fileManager = new FileManager();

    private GridPane gameLost = new GridPane();
    private Stage endStage = new Stage();
    private Button resetButton = new Button("Reset");
    private Board board = new Board();
    Label highScoreText = new Label("Highscore");
    VBox vbox = new VBox();
    Label highScoreValue = new Label("0");

    @Override
    public void start(Stage gameStage) {

        highScoreValue.setText(fileManager.load());

        board.spawn();
        board.spawn();
        updateUI(gridPane, board);
        vbox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

        updateUI(gridPane, board);

        vbox.getChildren().addAll(highScoreText, highScoreValue, gridPane);


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
                    break;
                    case DOWN:
                case S:
                    board.moveDown();
                    break;
                    case LEFT:
                case A:
                    board.moveLeft();
                    break;
                    case RIGHT:
                case D:
                    board.moveRight();
                    break;
                default:
                    break;
            }
            updateUI(gridPane,board);
            if(!board.isSpawned()){
                gameEnded();
            }
            setHighScore(board.getHighestNumber());
        });
    }


    public void gameEnded() {
        fileManager.save(board);
        gameLost.add(resetButton,0,0);
        Scene endScene = new Scene(gameLost, 400, 200);
        endStage.setScene(endScene);
        endStage.show();
    }

    public void setHighScore(int checkHighScore) {
        if(checkHighScore > getHighScore()) {
            highScoreValue.setText(String.valueOf(checkHighScore));
        }
    }

    public int getHighScore() {
        return Integer.parseInt(highScoreValue.getText());
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
