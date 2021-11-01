package modifier;

import java.awt.*;

public class GreyAvgModifier extends ColorModifier {
    @Override
    public Color modify(Color color, modifier.color.Color modifierColor, double modifierValue) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int avg = (red + green + blue) / 3;

        return new Color(
                avg,
                avg,
                avg
        );
    }
}
