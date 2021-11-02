package modifier.color;

import java.awt.*;

public abstract class ColorModifier {
    public abstract Color modify(Color color, modifier.color.single.Color modifierColor, double modifierValue);
}
