package view.image;

import modifier.color.single.Color;

public class ColorSelectExtractor {
    public static Color parse(String string) {
        switch (string) {
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "blue":
                return Color.BLUE;
            default:
                throw new RuntimeException("Cannot parse selected color: " + string);
        }
    }
}
