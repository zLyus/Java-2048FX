package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

import java.io.Serializable;
import java.util.ArrayList;

public class Game2048 extends Application implements Serializable {

    public static final int GRID_SIZE = 4;
    public static final int SQUARE_SIZE = 100;

    private GridPane gridPane = new GridPane();
    private Stage gameStage = new Stage();
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
    private Button changeThemeButton = new Button("Change Theme");
    private Button resetHighScoreButton = new Button("Reset Highscore");
    private ObservableList<String> observlist = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(observlist);
    private Stage lastGamesStage = new Stage();
    private GridPane lastGamesGridPane = new GridPane();
    private Button lastGamesButton = new Button("Last Games");
    private Button backToGameButton = new Button("Back to Game");
    private Button themeChanged = new Button("Back to Game");
    private TileColor colorPicker;
    private GridPane firstGrid = new GridPane();
    private Button goToGameButton = new Button("Lets Play!");
    private Button startNewGameButton = new Button("Start new Game");
    private Button resetLastGames = new Button("Reset Last Games");
    private ComboBox<String> comboBox = new ComboBox<>();
    private GridPane changeThemeGridPane = new GridPane();
    private Stage changeThemeStage = new Stage();
    private Label instruction = new Label("Hello, this is a game where you need to connect the same numbers with each other so the tiles merge into one. The goal is to reach the number 2048 by combining tiles. Use the WASD keys to move the tiles up, left, down, or right. When two tiles with the same number touch, they merge into one with the sum of the two numbers. Keep combining tiles to create larger numbers. Before you start please select a Color theme (you can always change it later). Can you reach 2048? Good luck!");
    private HBox row1 = new HBox();
    private HBox row2 = new HBox();
    private HBox row3 = new HBox();
    private int indexToAdd = 0;
    private boolean hasToBeOverwritten = false;


    @Override
    public void start(Stage firstStage) {

        /**
         * Loads the Highscore and Lastgames that were saved the last time the user played this game
         */
        setHighScore(fileManager.loadHighScore(), false);
        setLastGames(fileManager.loadLastGames());

        /**
         * Designs the mainStage where the Game is played
         */
        row1.getChildren().addAll(startNewGameButton, resetHighScoreButton, lastGamesButton, changeThemeButton);
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
        gameStage.setScene(gameScene);
        gameStage.setTitle("Game 2048");
        gameStage.show();
        board.spawn();
        board.spawn();
        updateUI(gridPane, board);

        listView.prefHeight(95);
        listView.prefWidth(100);

        /**
         * Designs the "FirstSTage", which shows a tutorial for the game and lets the user select a color theme
         */
        comboBox.getItems().addAll("Red", "Blue", "Green");

        firstGrid.add(instruction,0,0);
        firstGrid.add(comboBox,0,1);
        firstGrid.add(goToGameButton,0,2);

        Scene FirstScene = new Scene(firstGrid,800,600);
        firstStage.setScene(FirstScene);
        firstStage.setTitle("Welcome!");
        firstStage.show();

        /**
         * Designs the "LastGamesGridPane", which shows the last 3 games the Player has finished and the achieved Score
         */
        lastGamesGridPane.add(listView,0,0);
        lastGamesGridPane.add(resetLastGames,1,1);
        lastGamesGridPane.add(backToGameButton,1,2);
        Scene scene = new Scene(lastGamesGridPane,400,200);
        lastGamesStage.setScene(scene);

        /**
         * Designs the "GameLostGridPane", which shows up when the Player lost
         */
        gameLost.add(restartButton,0,0);
        Scene endScene = new Scene(gameLost, 400, 200);
        endStage.setScene(endScene);

        /**
         * Designs the "ChangeThemeStage", which lets the user change the theme they selected
         */
        changeThemeGridPane.add(comboBox,0,0);
        changeThemeGridPane.add(themeChanged,1,0);
        Scene changeThemeScene = new Scene(changeThemeGridPane, 400, 200);
        changeThemeStage.setScene(changeThemeScene);
        changeThemeStage.setTitle("Change Theme");

        resetLastGames.setOnAction(event -> {
            listView.getItems().clear();
           // fileManager.saveLastGames(listView, true);
        });

        startNewGameButton.setOnAction(event -> {
            board.clearBoard();
            setCurrentScore(0,true);
            updateUI(gridPane, board);
        });

        themeChanged.setOnAction(event -> {
            if(comboBox.getSelectionModel().getSelectedItem() != null) {
                colorPicker = new TileColor(comboBox.getSelectionModel().getSelectedItem());
            }
            changeThemeStage.hide();
            updateUI(gridPane, board);
        });

        changeThemeButton.setOnAction(event -> {
            changeThemeStage.show();
        });

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

        goToGameButton.setOnAction(event -> {
            if(comboBox.getSelectionModel().getSelectedItem() != null) {
                colorPicker = new TileColor(comboBox.getSelectionModel().getSelectedItem());
            } else {
                colorPicker = new TileColor("Red");
            }
            firstStage.hide();
            updateUI(gridPane, board);
        });


        gameStage.setOnCloseRequest(event -> {
           // fileManager.saveLastGames(convertListView());
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
            if(!board.isSpawned()) {
                addLastGamesToListView();
                endStage.show();
                fileManager.saveHighScore(board, false);
            }
            fileManager.saveHighScore(board, false);
            int currentHighest = board.getHighestNumber();
            setCurrentScore(currentHighest, false);
            setHighScore(currentHighest, false);
        });
    }
    public void showMainStage() {
        Scene gameScene = new Scene(vbox, 600,500);

        gameStage.setTitle("2048Game");
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    public ArrayList<String> convertListView() {
        ArrayList<String> list = new ArrayList<>();
        for(String item : observlist) {
            if(item != null || !(item.equals(""))) {
                list.add(item);
            }
        }
        return list;
    }

    public void addLastGamesToListView() {
        if(hasToBeOverwritten) {
            listView.getItems().remove((indexToAdd));
        }
        listView.getItems().add(indexToAdd, (indexToAdd + 1) + ".) Achieved Score : " + getCurrentScore());
        indexToAdd++;
        if(indexToAdd >= 3) {
            hasToBeOverwritten = true;
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

    public void setLastGames(ArrayList<String> list) {
        for(String item : list) {
            if(item != null || !(item.isEmpty())) {
                listView.getItems().add(item);
            }
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
                Tile currentTile = board.getTile(col, row);

                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.WHITE);

                square.setStroke(Color.BLACK);

                Text text;
                if (currentTile != null) {
                    text = new Text(String.valueOf(currentTile.getNumber()));
                    if(colorPicker != null) {
                        square.setFill(colorPicker.getColor(currentTile.getNumber()));
                    } else {

                    }
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
                } else {
                    text = new Text(" ");
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
                }
                StackPane stackPane = new StackPane(square, text);
                GridPane.setRowIndex(stackPane, col);
                GridPane.setColumnIndex(stackPane, row);
                gridPane.getChildren().add(stackPane);
            }
        }
    }
}
