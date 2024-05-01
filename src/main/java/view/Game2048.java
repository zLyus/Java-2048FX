package view;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class Game2048 extends Application {

    public static final int GRID_SIZE = 4;
    public static final int SQUARE_SIZE = 100;

    private GridPane gridPane = new GridPane();
    private FileManager fileManager = new FileManager();
    private GridPane gameLost = new GridPane();
    private Stage endStage = new Stage();
    private Button restartButton = new Button("RestartGame");
    private Board board = new Board();
    private Label highScoreText = new Label("Highscore: ");
    private VBox vbox = new VBox();
    private Label highScoreValue = new Label("0");
    private Label currentScoreText = new Label("Current Score: ");
    private Label currentScoreValue = new Label("0");
    private Button resetHighScoreButton = new Button("Reset Highscore");
    private SaveGames saveGames = new SaveGames();
    private ObservableList<String> observlist = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(observlist);
    private Stage lastGamesStage = new Stage();
    private GridPane lastGamesGridPane = new GridPane();
    private Button lastGamesButton = new Button("Last Games");
    private Button backToGameButton = new Button("Back to Game");
    private HBox row1 = new HBox();
    private HBox row2 = new HBox();
    private HBox row3 = new HBox();
    private int indexToAdd = 0;


    @Override
    public void start(Stage gameStage) {

        setHighScore(fileManager.loadHighScore(), false);

        lastGamesGridPane.add(listView,0,0);
        lastGamesGridPane.add(backToGameButton,1,0);
        Scene scene = new Scene(lastGamesGridPane,400,200);
        lastGamesStage.setScene(scene);

        fileManager.saveHighScore(board, false);
        Game currentGame = new Game(getCurrentScore());
        saveGames.add(currentGame);
        gameLost.add(restartButton,0,0);
        Scene endScene = new Scene(gameLost, 400, 200);
        endStage.setScene(endScene);

        board.spawn();
        board.spawn();
        updateUI(gridPane, board);

        listView.prefHeight(95);
        listView.prefWidth(100);

        System.out.println("lastGames width: " + listView.getWidth());
        System.out.println("lastGames height: " + listView.getHeight());


        row1.getChildren().addAll(resetHighScoreButton, lastGamesButton);
        row2.getChildren().addAll(highScoreText, highScoreValue, currentScoreText, currentScoreValue);
        row3.getChildren().addAll(gridPane);

        row1.setSpacing(10);
        row2.setSpacing(10);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(row1,row2,row3);

        Scene gameScene = new Scene(vbox, 600,500);

        gameStage.setTitle("2048Game");
        gameStage.setScene(gameScene);
        gameStage.show();

        lastGamesButton.setOnAction(event -> {
            lastGamesStage.show();
        });

        backToGameButton.setOnAction(event -> {
            lastGamesStage.hide();
        });

        restartButton.setOnAction(event -> {
            board.clearBoard();
            board.spawn();
            board.spawn();
            updateUI(gridPane,board);
            endStage.hide();
            setCurrentScore(0, true);
        });

        resetHighScoreButton.setOnAction(event -> {
            setHighScore(0,true);
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
                addLastGamesToListView();
                endStage.show();
            }
            int currentHighest = board.getHighestNumber();
            setCurrentScore(currentHighest, false);
            setHighScore(currentHighest, false);
        });
    }

    public void addLastGamesToListView() {
        listView.
        listView.getItems().add(indexToAdd, indexToAdd + ".) Score :" + getCurrentScore());
        indexToAdd++;
        if(indexToAdd >= 3) {
            indexToAdd = 0;
        }
    }


    public void setHighScore(int checkHighScore, boolean reset) {
        if(!reset) {
            if(checkHighScore > getHighScore()) {
                highScoreValue.setText(String.valueOf(checkHighScore));
            }
        } else {
            highScoreValue.setText("0");
        }
    }


    public void setCurrentScore(int checkCurrentScore, boolean reset) {
        System.out.println(checkCurrentScore);
        if(!reset) {
            if(checkCurrentScore > getCurrentScore()) {
                currentScoreValue.setText(String.valueOf(checkCurrentScore));
            }
        } else {
            currentScoreValue.setText("0");
        }

    }

    public int getHighScore() {
        return Integer.parseInt(highScoreValue.getText());
    }

    public int getCurrentScore() {
        return Integer.parseInt(currentScoreValue.getText());
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
