package modifier;

import java.awt.*;

public class GreyWeightAvgModifier extends ColorModifier {
    double RED_RATE = 2.5;
    double GREEN_RATE = 1.09;
    double BLUE_RATE = 1.11;

    @Override
    public Color modify(Color color, modifier.color.Color modifierColor, double modifierValue) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int avg = (int) ((
                (RED_RATE * red) + (GREEN_RATE * green) + (BLUE_RATE * blue))
                / (RED_RATE + GREEN_RATE + BLUE_RATE));

        return new Color(
                avg,
                avg,
                avg
        );
    }
}
