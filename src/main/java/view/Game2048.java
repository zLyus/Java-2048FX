package view;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    static int GRID_SIZE;
    public static int SQUARE_SIZE;

    private GridPane gridPane = new GridPane();
    private Stage gameStage = new Stage();
    private FileManager fileManager = new FileManager();
    private Stage endStage = new Stage();
    private Button restartButton = new Button("RestartGame");
    private Board board;
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
    private Button themeChangedButton = new Button("Back to Game");
    private TileColor colorPicker;
    private Button goToGameButton = new Button("Lets Play!");
    private Button startNewGameButton = new Button("Start new Game");
    private Button resetLastGames = new Button("Reset Last Games");
    private ComboBox<String> themeBox = new ComboBox<>();
    private GridPane changeThemeGridPane = new GridPane();
    private Stage changeThemeStage = new Stage();
    private ComboBox<String> changeBox = new ComboBox<>();
    private TextField gridInput = new TextField();
    private Controller ctrl;
    private ButtonType confirmButton = new ButtonType("Confirm");
    private ButtonType cancelButton = new ButtonType("Cancel");
    private Label instruction = new Label("How to play: Use your arrow keys to move the tiles. Tiles with the same number merge into one when they touch. Add them up to reach 2048!");
    private HBox row1 = new HBox();
    private HBox row2 = new HBox();
    private HBox row3 = new HBox();
    private HBox firstRow1 = new HBox();
    private HBox firstRow2 = new HBox();
    private HBox firstRow3 = new HBox();
    private HBox firstRow4 = new HBox();
    private HBox firstRow5 = new HBox();
    private int indexToAdd = 0;
    int size;


    @Override
    public void start(Stage firstStage) {
        /**
         * Loads the Highscore and Lastgames that were saved the last time the user played this game
         */
        fileManager.saveLastGames(convertListView(), true);
        fileManager.saveHighScore(board,true);
        setHighScore(fileManager.loadHighScore(), false);
        setLastGames(fileManager.loadLastGames());

        /**
         * Designs the gameStage where the Game is played
         */

        gridPane.setFocusTraversable(true);

        row1.getChildren().addAll(startNewGameButton, resetHighScoreButton, lastGamesButton, changeThemeButton);
        row2.getChildren().addAll(highScoreText, highScoreValue, currentScoreText, currentScoreValue);
        row3.getChildren().addAll(gridPane);

        row1.setSpacing(10);
        row2.setSpacing(10);
        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(row1, row2, row3);
        Scene gameScene = new Scene(vbox, 700, 600);
        gameStage.setScene(gameScene);
        gameStage.setTitle("Game 2048");


        /**
         * Designs the "FirstStage", which shows a tutorial for the game and lets the user select a color theme
         */
        themeBox.getItems().addAll("Red", "Purple", "Green");
        themeBox.setValue("Select Theme");

        gridInput.setPrefSize(160,1);
        gridInput.setPromptText("Select Gridsize, example '4' ");

        instruction.setWrapText(true);
        instruction.setPadding(new Insets(10));

        firstRow1.getChildren().add(instruction);
        firstRow2.getChildren().add(themeBox);
        firstRow3.getChildren().add(gridInput);
        firstRow4.getChildren().add(goToGameButton);

        firstRow2.setAlignment(Pos.CENTER);
        firstRow3.setAlignment(Pos.CENTER);
        firstRow4.setAlignment(Pos.CENTER);

        VBox firstVbox = new VBox();
        firstVbox.setSpacing(5);
        firstVbox.getChildren().addAll(firstRow1, firstRow2, firstRow3, firstRow4, firstRow5);

        Scene FirstScene = new Scene(firstVbox, 400, 250);
        firstStage.setScene(FirstScene);
        firstStage.setTitle("Welcome!");
        firstStage.show();

        /**
         * Designs the "LastGamesGridPane", which shows the last 3 games the Player has finished and the achieved Score
         */

        listView.prefHeight(95);
        listView.prefWidth(100);

        lastGamesGridPane.add(listView, 0, 0);
        lastGamesGridPane.add(resetLastGames, 1, 1);
        lastGamesGridPane.add(backToGameButton, 1, 2);
        Scene scene = new Scene(lastGamesGridPane, 400, 200);
        lastGamesStage.setScene(scene);

        /**
         * Designs the "ChangeThemeStage", which lets the user change the theme they selected
         */

        changeBox.getItems().addAll("Red", "Purple", "Green");

        changeThemeGridPane.add(changeBox, 0, 0);
        changeThemeGridPane.add(themeChangedButton, 1, 0);

        Scene changeThemeScene = new Scene(changeThemeGridPane, 400, 200);
        changeThemeStage.setScene(changeThemeScene);
        changeThemeStage.setTitle("Change Theme");

        goToGameButton.setOnAction(event -> {
            if (themeBox.getSelectionModel().getSelectedItem() != null) {
                colorPicker = new TileColor(themeBox.getSelectionModel().getSelectedItem());
            } else {
                colorPicker = new TileColor("Red");
            }

            String selectedValue = gridInput.getText();

            if (!selectedValue.equals("")) {
                size = Integer.parseInt(selectedValue);
                //if(size > 0 && size <)
                System.out.println(size);
                board = new Board(size);
                GRID_SIZE = size;
                SQUARE_SIZE = (150 - size * 10);
            } else {
                GRID_SIZE = 4;
                SQUARE_SIZE = (150 - 4 * 10);
                board = new Board(4);
            }

            ctrl = new Controller(board);
            firstStage.hide();
            gameStage.show();
            ctrl.startSpawn();
            updateUI(gridPane, ctrl.getBoard());
        });

        resetLastGames.setOnAction(event -> {
            listView.getItems().clear();
            fileManager.saveLastGames(convertListView(), true);
        });

        startNewGameButton.setOnAction(event -> {
            Alert confirmAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmAlert.setTitle("Confirm");
            confirmAlert.setHeaderText("Are you sure you want to start a new game?");
            confirmAlert.setContentText("All progress will be lost, press Confirm to continue");

            confirmAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == confirmButton) {
                    addLastGamesToListView();
                    ctrl.clearBoard();
                    setCurrentScore(0, true);
                    ctrl.startSpawn();
                    updateUI(gridPane, ctrl.getBoard());
                }
            });


        });

        themeChangedButton.setOnAction(event -> {
            if (changeBox.getSelectionModel().getSelectedItem() != null) {
                colorPicker = new TileColor(changeBox.getSelectionModel().getSelectedItem());
            } else {
                System.out.println("No color selected");
            }
            changeThemeStage.hide();
            updateUI(gridPane, ctrl.getBoard());
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
            ctrl.clearBoard();
            ctrl.startSpawn();
            updateUI(gridPane, ctrl.getBoard());
            endStage.hide();
            setCurrentScore(0, true);
        });

        resetHighScoreButton.setOnAction(event -> {
            setHighScore(0, true);

        });

        gameStage.setOnCloseRequest(event -> {
            fileManager.saveLastGames(convertListView(), false);
        });

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                case W:
                    ctrl.moveUp();
                    break;
                case DOWN:
                case S:
                    ctrl.moveDown();
                    break;
                case LEFT:
                case A:
                    ctrl.moveLeft();
                    break;
                case RIGHT:
                case D:
                    ctrl.moveRight();
                    break;
                default:
                    break;
            }

            updateUI(gridPane, ctrl.getBoard());
            if (!ctrl.isSpawned()) {
                addLastGamesToListView();

                Alert lostAlert = new Alert(Alert.AlertType.INFORMATION);
                lostAlert.setTitle("You Lost!");
                lostAlert.setHeaderText("Your Score was: " + getCurrentScore());
                lostAlert.setContentText("There wasn´t any space left so you lost");
                lostAlert.getButtonTypes().set(0, ButtonType.OK);

                lostAlert.showAndWait().ifPresent(response -> {
                    setCurrentScore(0, true);
                    if (response == ButtonType.OK) {
                        ctrl.clearBoard();
                        ctrl.startSpawn();
                        updateUI(gridPane, ctrl.getBoard());
                    }
                });
                lostAlert.setOnCloseRequest(Request -> {
                    setCurrentScore(0, true);
                    ctrl.clearBoard();
                });
                fileManager.saveHighScore(ctrl.getBoard(), false);
            }
            fileManager.saveHighScore(ctrl.getBoard(), false);
            int currentHighest = ctrl.getBoard().getHighestNumber();
            setCurrentScore(currentHighest, false);
            setHighScore(currentHighest, false);
        });
    }

    public void showMainStage() {
        gameStage.show();
    }

    public ArrayList<String> convertListView() {
        ArrayList<String> list = new ArrayList<>();
        for (String item : observlist) {
            if (item != null || !(item.equals(""))) {
                list.add(item);
            }
        }
        return list;
    }

    public void startSpawn() {
        ctrl.startSpawn();
    }


    public void addLastGamesToListView() {

        if (indexToAdd < listView.getItems().size()) {
            listView.getItems().remove(indexToAdd);
        }

        listView.getItems().add(indexToAdd, (indexToAdd + 1) + ".) Achieved Score : " + getCurrentScore() + " Size: " + board.getBoardSize());

        indexToAdd++;

        if (indexToAdd >= 3) {
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
        if(!reset) {
            if(checkCurrentScore > getCurrentScore()) {
                currentScoreValue.setText(String.valueOf(checkCurrentScore));
            }
        } else {
            currentScoreValue.setText("0");
        }

    }

    public void setLastGames(ArrayList<String> list) {
        if(list != null) {
            for (String item : list) {
                if (item != null || !(item.isEmpty())) {
                    listView.getItems().add(item);
                }
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
                Tile currentTile = ctrl.getTile(col, row);

                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.color(0.96695 , 0.95335 , 0.92785));
                square.setStroke(Color.BLACK);

                Text text;
                if (currentTile != null) {
                    text = new Text(String.valueOf(ctrl.getNumber(currentTile)));
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
