package model;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class TileColor {

    private String currentTheme;
    public boolean usingCustomColors; // Flag to indicate if custom colors are being used

    private Map<Integer, Color> colorMap;
    private Color startColor;
    private Color endColor;
    private Map<Integer, Double> tileRatios;

    // Constructor for predefined themes
    public TileColor(String theme) {
        currentTheme = theme;
        usingCustomColors = false;
        colorMap = new HashMap<>();
        initializeColorMap();
    }

    // Constructor for custom color transitions
    public TileColor(Color startColor) {
        this.usingCustomColors = true;
        this.startColor = startColor;
        this.endColor = Color.BLACK;
        tileRatios = new HashMap<>();
        initializeTileRatios();
    }

    /**
     * Colors for Theme Presets
     */
    private void initializeColorMap() {
        colorMap = new HashMap<>();
        switch (currentTheme) {
            case "Red":
                colorMap.put(2, Color.web("#eee4da"));
                colorMap.put(4, Color.web("#ede0c8"));
                colorMap.put(8, Color.web("#f2b179"));
                colorMap.put(16, Color.web("#f59563"));
                colorMap.put(32, Color.web("#f67c5f"));
                colorMap.put(64, Color.web("#f65e3b"));
                colorMap.put(128, Color.web("#edcf72"));
                colorMap.put(256, Color.web("#edcc61"));
                colorMap.put(512, Color.web("#edc850"));
                colorMap.put(1024, Color.web("#edc53f"));
                colorMap.put(2048, Color.web("#edc22e"));
                break;
            case "Purple":
                colorMap.put(2, Color.web("#eee4f5"));
                colorMap.put(4, Color.web("#cdc1ff"));
                colorMap.put(8, Color.web("#bb86fc"));
                colorMap.put(16, Color.web("#985eff"));
                colorMap.put(32, Color.web("#6200ee"));
                colorMap.put(64, Color.web("#3700b3"));
                colorMap.put(128, Color.web("#6200ea"));
                colorMap.put(256, Color.web("#5f3dc4"));
                colorMap.put(512, Color.web("#3d5af1"));
                colorMap.put(1024, Color.web("#2c3f87"));
                colorMap.put(2048, Color.web("#1a237e"));
                break;
            case "Green":
                colorMap.put(2, Color.web("#e8f5e9"));
                colorMap.put(4, Color.web("#c8e6c9"));
                colorMap.put(8, Color.web("#a5d6a7"));
                colorMap.put(16, Color.web("#81c784"));
                colorMap.put(32, Color.web("#66bb6a"));
                colorMap.put(64, Color.web("#4caf50"));
                colorMap.put(128, Color.web("#43a047"));
                colorMap.put(256, Color.web("#388e3c"));
                colorMap.put(512, Color.web("#2e7d32"));
                colorMap.put(1024, Color.web("#1b5e20"));
                colorMap.put(2048, Color.web("#0d5302"));
                break;
        }
    }

    /**
     * Generates the ratio for the transition between colors for each number
     */
    private void initializeTileRatios() {
        int[] tileNumbers = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
        for (int i = 0; i < tileNumbers.length; i++) {
            double ratio = (double) i / (tileNumbers.length - 1);
            tileRatios.put(tileNumbers[i], ratio);
        }
    }

    /**
     * Generates Color with the ratio that each number has been initialized with
     * @param ratio - ratio for the transition
     * @return Color with a transition for given Ratio
     */
    private Color interpolateColor(double ratio) {
        // Convert start and end colors to grayscale equivalents
        double startGray = startColor.getRed() * 0.3 + startColor.getGreen() * 0.59 + startColor.getBlue() * 0.11;
        double endGray = endColor.getRed() * 0.3 + endColor.getGreen() * 0.59 + endColor.getBlue() * 0.11;

        // Mix grayscale equivalents with original colors based on the ratio
        double red = (1 - ratio) * startColor.getRed() + ratio * startGray;
        double green = (1 - ratio) * startColor.getGreen() + ratio * startGray;
        double blue = (1 - ratio) * startColor.getBlue() + ratio * startGray;

        // Create the desaturated color
        Color color = Color.rgb((int) (red * 255), (int) (green * 255), (int) (blue * 255));

        return color;
    }


    /**
     *
     * @param number - Number of the Tile
     * @return Color that has been changed dependent on @number
     */
    public Color getColor(int number) {
        if (!usingCustomColors) {
            return colorMap.getOrDefault(number, Color.web("#cdc1b4"));
        } else {
            double ratio = tileRatios.getOrDefault(number, 0.0);
            return interpolateColor(ratio);
        }
    }

    public void setCustom(Color start) {
        usingCustomColors = true;
        this.startColor = start;
        this.endColor = Color.BLACK;

        tileRatios = new HashMap<>();
        colorMap = new HashMap<>();
        initializeTileRatios();
    }

    public void setPreset(String color) {
        usingCustomColors = false;
        this.currentTheme = color;
        colorMap = new HashMap<>();
        initializeColorMap();

    }

}
