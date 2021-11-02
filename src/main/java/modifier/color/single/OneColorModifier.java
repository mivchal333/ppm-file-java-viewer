package modifier.color.single;

import modifier.color.ColorModifier;

public abstract class OneColorModifier extends ColorModifier {
    int MIN_COLOR_BOUND = 0;
    int MAX_COLOR_BOUND = 255;

    public abstract int processPixel(int color, double value);

    public java.awt.Color modify(java.awt.Color color, Color modifierColor, double value) {
        switch (modifierColor) {
            case RED:
                return processRed(color, value);
            case GREEN:
                return processGreen(color, value);
            default:
                return processBlue(color, value);
        }
    }

    private java.awt.Color processRed(java.awt.Color color, double value) {
        int red = color.getRed();

        return new java.awt.Color(
                processPixel(red, value),
                color.getGreen(),
                color.getBlue()
        );
    }

    private java.awt.Color processGreen(java.awt.Color color, double value) {
        int green = color.getGreen();

        return new java.awt.Color(
                color.getRed(),
                processPixel(green, value),
                color.getBlue()
        );
    }

    private java.awt.Color processBlue(java.awt.Color color, double value) {
        int blue = color.getBlue();

        return new java.awt.Color(
                color.getRed(),
                color.getGreen(),
                processPixel(blue, value)
        );
    }


}
