package view;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Game2048 extends Application implements Serializable {

    public int GRID_SIZE;
    public int SQUARE_SIZE;
    private GridPane gridPane = new GridPane();
    private Stage gameStage = new Stage();
    private FileManager fileManager = new FileManager();
    private Stage endStage = new Stage();
    private Button restartButton = new Button("RestartGame");
    private Button changeThemeButton = new Button("Change Theme");
    private Board board;
    private Label highScoreText = new Label("Highscore: ");
    private VBox vbox = new VBox();
    private Label highScoreValue = new Label("0");
    private Label currentScoreText = new Label("Current Score: ");
    private Label currentScoreValue = new Label("0");
    private Button resetHighScoreButton = new Button("Reset Highscore");
    private ObservableList<String> observlist = FXCollections.observableArrayList();
    private ListView<String> listView = new ListView<>(observlist);
    private Stage lastGamesStage = new Stage();
    private GridPane lastGamesGridPane = new GridPane();
    private Button lastGamesButton = new Button("Last Games");
    private Button backToGameButton = new Button("Back to Game");
    private Button themeChangedButton = new Button("Back to Game");
    private TileColor colorPicker;
    private Stage firstStage = new Stage();
    private Button goToGameButton = new Button("Lets Play!");
    private Button startNewGameButton = new Button("Start new Game");
    private Button resetLastGames = new Button("Reset Last Games");
    private ComboBox<String> themeBox = new ComboBox<>();
    private GridPane changeThemeGridPane = new GridPane();
    private Stage changeThemeStage = new Stage();
    private Label orLabel = new Label("Or");
    private Label orLabel2 = new Label("Or use Custom Colors:");
    private Label startInstruction2 = new Label("starting Color");
    private TextField startInput2 = new TextField();
    private ComboBox<String> changeBox = new ComboBox<>();
    private TextField gridInput = new TextField();
    private Controller ctrl;
    private Label continueLabel = new Label("Do you want to continue Last Game?");
    private Button continueButton = new Button("Continue");
    private Button dontContinueButton = new Button("Start a new Game");
    private GridPane continueGrid = new GridPane();
    private Stage customStage = new Stage();
    private GridPane customGridPane = new GridPane();
    private Label startInstruction = new Label("starting Color");
    private TextField startInput = new TextField();
    private Button customButton = new Button("Custom Theme");
    private Button finishedCustomButton = new Button("Finish");
    private Label instruction = new Label("How to play: Use your arrow keys to move the tiles. When two tiles with the same number touch, they merge into one!");
    private HBox row1 = new HBox();
    private HBox row2 = new HBox();
    private HBox row3 = new HBox();
    private HBox firstRow1 = new HBox();
    private HBox firstRow2 = new HBox();
    private HBox firstRow3 = new HBox();
    private HBox firstRow4 = new HBox();
    private HBox firstRow5 = new HBox();
    private int indexToAdd = 0;
    int size = 0;
    boolean justreSized = false;


    @Override
    public void start(Stage continueStage) {

        /**
         * Loads the Highscore and Lastgames that were saved the last time the user played this game
         */
        System.out.println(fileManager.loadBoard());
        if(fileManager.loadBoard() == null) {
            System.out.println("eraaaa");
            continueStage.hide();
            firstStage.show();
        }

        setHighScore(fileManager.loadHighScore(), false);
        setLastGames(fileManager.loadLastGames());
        setCustomTheme(fileManager.loadCustomTheme());

        /**
         * Designs the mainStage where the Game is played
         */
        startNewGameButton.setFocusTraversable(false);
        resetHighScoreButton.setFocusTraversable(false);
        lastGamesButton.setFocusTraversable(false);
        changeThemeButton.setFocusTraversable(false);
        row1.setFocusTraversable(false);

        row1.getChildren().addAll(startNewGameButton, resetHighScoreButton, lastGamesButton, changeThemeButton);
        row2.getChildren().addAll(highScoreText, highScoreValue, currentScoreText, currentScoreValue);
        row3.getChildren().addAll(gridPane);

        setMainStageCenter();

        row1.setSpacing(10);
        row2.setSpacing(10);

        vbox.getChildren().addAll(row1, row2, row3);

        Scene gameScene = new Scene(vbox, 550, 550);

        gameStage.setScene(gameScene);
        gameStage.setTitle("Game 2048");

        gameScene.widthProperty().addListener((observable, oldSceneWidth, newSceneWidth) -> {
            if (!justreSized) {
                gameStage.setHeight((Double) newSceneWidth);
                setMainStageCenter();
                justreSized = true;
            } else {
                justreSized = false;
            }
        });

        gameScene.heightProperty().addListener((observable, oldSceneHeight, newSceneHeight) -> {
            if (!justreSized) {
                gameStage.setWidth((Double) newSceneHeight);
                setMainStageCenter();
                justreSized = true;
            } else {
                justreSized = false;
            }
        });

        /**
         * Designs the "FirstStage", which shows a tutorial for the game and lets the user select a color theme
         */
        themeBox.getItems().addAll("Red", "Purple", "Green");
        themeBox.setValue("Select Theme");

        gridInput.setPrefSize(160, 1);
        gridInput.setPromptText("Select Gridsize, example '4' ");

        instruction.setWrapText(true);
        instruction.setStyle("-fx-alignment: center; -fx-text-alignment: center;");

        Region space1 = new Region();
        space1.setPrefWidth(10);
        Region space2 = new Region();
        space2.setPrefWidth(10);

        firstRow1.getChildren().add(instruction);
        firstRow2.getChildren().addAll(themeBox, space1, orLabel, space2, customButton);
        firstRow3.getChildren().add(gridInput);
        firstRow4.getChildren().add(goToGameButton);

        firstRow1.setAlignment(Pos.CENTER);
        firstRow2.setAlignment(Pos.CENTER);
        firstRow3.setAlignment(Pos.CENTER);
        firstRow4.setAlignment(Pos.CENTER);

        VBox firstVbox = new VBox();
        firstVbox.setSpacing(5);
        firstVbox.getChildren().addAll(firstRow1, firstRow2, firstRow3, firstRow4, firstRow5);

        Scene FirstScene = new Scene(firstVbox, 300, 150);
        firstStage.setScene(FirstScene);
        firstStage.setTitle("Welcome!");
        firstStage.show();

        /**
         * Designs the "ContinueStage", which lets the user play the game where they last stopped
         */
        continueGrid.add(continueLabel,0,0);
        continueGrid.add(continueButton,1,0);
        continueGrid.add(dontContinueButton,2,0);

        /**
         * Designs the "CustomStage", which lets the user enter Colors (in hex code) for a custom Theme
         */
        startInput.setPromptText("hex Code");

        customGridPane.add(startInstruction, 0, 0);
        customGridPane.add(startInput, 1, 0);
        customGridPane.add(finishedCustomButton, 0, 2);

        customGridPane.setPadding(new Insets(10));

        Scene customScene = new Scene(customGridPane, 300, 150);
        customStage.setScene(customScene);
        customStage.setTitle("Custom Themes");

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
        changeBox.setPromptText("Themes");

        changeThemeGridPane.add(changeBox, 0, 0);
        changeThemeGridPane.add(orLabel2, 0, 1);
        changeThemeGridPane.add(startInstruction2, 0, 2);
        changeThemeGridPane.add(startInput2, 1, 2);
        changeThemeGridPane.add(themeChangedButton, 0, 5);


        Scene changeThemeScene = new Scene(changeThemeGridPane, 550, 200);
        changeThemeStage.setScene(changeThemeScene);
        changeThemeStage.setTitle("Change Theme");

        customButton.setOnAction(event -> {
            firstStage.hide();
            customStage.show();
        });

        finishedCustomButton.setOnAction(event -> {
            customStage.hide();
            firstStage.show();
            fileManager.saveCustomTheme(convertCustomThemes());
        });

        /**
         * Creates TileColor "colorpicker", which manages the color of each Tile
         */
        goToGameButton.setOnAction(event -> {
            if (startInput.getText() == null || startInput.getText().isEmpty()) {
                if (themeBox.getSelectionModel().getSelectedItem() == null) {
                    colorPicker = new TileColor("Red");
                } else {
                    colorPicker = new TileColor(themeBox.getSelectionModel().getSelectedItem());
                }
            } else {
                String startColor = startInput.getText().trim();
                if (isValidHexCode(startColor)) {
                    colorPicker = new TileColor(Color.web(startColor));
                }
            }

            String selectedValue = gridInput.getText();

            if (selectedValue != null && !selectedValue.isEmpty()) {
                try {
                    size = Integer.parseInt(selectedValue);
                    if (size > 0) {
                        board = new Board(size);
                        GRID_SIZE = size;
                        SQUARE_SIZE = 400 / size;
                    } else {
                        board = new Board(4);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Something went wrong, lets just go with a 4x4");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            board = new Board(4);
                            GRID_SIZE = 4;
                            SQUARE_SIZE = 400 / 4;
                        }
                    });
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong, lets just go with a 4x4");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        board = new Board(4);
                        GRID_SIZE = 4;
                        SQUARE_SIZE = 400 / 4;
                    }
                });
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
            addLastGamesToListView();
            ctrl.clearBoard();
            setCurrentScore(0, true);
            ctrl.startSpawn();
            updateUI(gridPane, ctrl.getBoard());
        });

        themeChangedButton.setOnAction(event -> {
            if (startInput2.getText().isEmpty()) {
                if (changeBox.getSelectionModel().getSelectedItem() != null) {
                    colorPicker.setPreset();
                }
            } else {
                String startColor = startInput2.getText().trim();

                if (isValidHexCode(startColor)) {
                    colorPicker.setCustom(Color.web(startColor));
                }
            }

            changeThemeStage.hide();
            updateUI(gridPane, ctrl.getBoard());

            fileManager.saveCustomTheme(convertCustomThemes());
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
            fileManager.saveHighScore(board, false);
            fileManager.saveCustomTheme(convertCustomThemes());
            fileManager.saveBoard(board);
        });

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP, W:
                    ctrl.moveUp();
                    break;
                case DOWN, S:
                    ctrl.moveDown();
                    break;
                case LEFT, A:
                    ctrl.moveLeft();
                    break;
                case RIGHT, D:
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

        dontContinueButton.setOnAction(event -> {
            continueStage.hide();
            firstStage.show();
        });

        continueButton.setOnAction(event -> {
            board = fileManager.loadBoard();
            continueStage.hide();
            firstStage.show();
        });

    }

    public void setCustomTheme(String str) {
        startInput.setText(str);
        startInput2.setText(str);
    }

    public String convertCustomThemes() {
        String str = new String();
        if (startInput.getText() != null && !startInput.getText().isEmpty()) {
            str = startInput.getText().trim();
        }
        if (startInput2.getText() != null && !startInput2.getText().isEmpty()) {
            str = startInput2.getText().trim();
        }
        return str;
    }

    public boolean isValidHexCode(String hexCode) {
        // Regular expression for validating hex color code (e.g., #RRGGBB or #RGB)
        String hexPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        return hexCode.matches(hexPattern);
    }

    public void setMainStageCenter() {
        vbox.setAlignment(Pos.CENTER);
        row1.setAlignment(Pos.CENTER);
        row2.setAlignment(Pos.CENTER);
        row3.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
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
        if (!reset) {
            if (checkHighScore > getHighScore()) {
                highScoreValue.setText(String.valueOf(checkHighScore));
            }
        } else {
            highScoreValue.setText("0");
        }
    }

    public void setCurrentScore(int checkCurrentScore, boolean reset) {
        if (!reset) {
            if (checkCurrentScore > getCurrentScore()) {
                currentScoreValue.setText(String.valueOf(checkCurrentScore));
            }
        } else {
            currentScoreValue.setText("0");
        }
    }

    public void setLastGames(ArrayList<String> list) {
        if (list != null) {
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
        int textSize = 105 / GRID_SIZE;
        gridPane.getChildren().clear(); // Clear the GridPane before updating
        for (int col = 0; col < GRID_SIZE; col++) {
            for (int row = 0; row < GRID_SIZE; row++) {
                Tile currentTile = ctrl.getTile(col, row);

                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.WHITE);

                // 0.733, 0.678, 0.627, 1.0 is the color of the official Game

                square.setStroke(Color.BLACK);
                square.setStrokeWidth(2);

                Text text;
                if (currentTile != null) {
                    text = new Text(String.valueOf(ctrl.getNumber(currentTile)));
                    if (colorPicker != null) {
                        square.setFill(colorPicker.getColor(ctrl.getNumber(currentTile)));
                    }
                    text.setFont(Font.font("Arial", FontWeight.BOLD, textSize));
                } else {
                    text = new Text(" ");
                    text.setFont(Font.font("Arial", FontWeight.BOLD, textSize));
                }
                StackPane stackPane = new StackPane(square, text);
                GridPane.setRowIndex(stackPane, col);
                GridPane.setColumnIndex(stackPane, row);
                gridPane.getChildren().add(stackPane);
            }
        }
    }
}
