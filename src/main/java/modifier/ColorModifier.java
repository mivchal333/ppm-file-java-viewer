package modifier;

import java.awt.*;

public abstract class ColorModifier {
    public abstract Color modify(Color color, modifier.color.Color modifierColor, double modifierValue);
}
