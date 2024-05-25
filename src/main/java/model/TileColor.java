package model;

import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

public class TileColor {

    private String currentTheme;
    private Map<Integer, Color> colorMap;

    public TileColor(String theme) {
        this.currentTheme = theme;
        colorMap = new HashMap<>();
        initializeColorMap();
    }

    private void initializeColorMap() {
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

    public Color getColor(int number) {
        return colorMap.getOrDefault(number, Color.web("#cdc1b4"));
    }

}
