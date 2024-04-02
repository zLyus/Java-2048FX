package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Board;
import model.Tile;

import static view.Game2048.GRID_SIZE;
import static view.Game2048.SQUARE_SIZE;

public class UI {

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

                    // Calculate the new position based on the tile's current and previous positions
                    double newX = (col + currentTile.getX()) * SQUARE_SIZE + SQUARE_SIZE / 2;
                    double newY = (row + currentTile.getY()) * SQUARE_SIZE + SQUARE_SIZE / 2;

                    // Animate the movement
                    animateTileMove(text, newX, newY);
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

        // Force JavaFX to recalculate the bounds of all child nodes
        gridPane.layout();
    }


    private void animateTileMove(Text text, double newX, double newY) {
        // Calculate the translation values based on the tile's current and previous positions
        double translateX = newX - text.getX();
        double translateY = newY - text.getY();

        // Create a new Timeline for the text
        Timeline timeline = new Timeline();

        // Define the end state of the animation
        KeyFrame end = new KeyFrame(Duration.seconds(0.5),
                new KeyValue(text.translateXProperty(), translateX),
                new KeyValue(text.translateYProperty(), translateY));

        // Add the end state to the timeline
        timeline.getKeyFrames().add(end);

        // Start the animation
        timeline.play();
    }
}