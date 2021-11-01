package modifier;

import modifier.color.AddColorModifier;

import java.awt.*;
import java.util.stream.Stream;

public class AddBrightnessModifier extends ColorModifier {
    @Override
    public Color modify(Color color, modifier.color.Color modifierColor, double modifierValue) {
        AddColorModifier addColorModifier = new AddColorModifier();

        return Stream.of(color)
                .map(mapColor -> addColorModifier.modify(mapColor, modifier.color.Color.RED, modifierValue))
                .map(mapColor -> addColorModifier.modify(mapColor, modifier.color.Color.GREEN, modifierValue))
                .map(mapColor -> addColorModifier.modify(mapColor, modifier.color.Color.BLUE, modifierValue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error while processing color chain!"));
    }
}
