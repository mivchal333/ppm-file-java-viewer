package modifier;

import modifier.color.SubtractColorModifier;

import java.awt.*;
import java.util.stream.Stream;

public class SubtractBrightnessModifier extends ColorModifier {
    @Override
    public Color modify(Color color, modifier.color.Color modifierColor, double modifierValue) {
        SubtractColorModifier operationModifier = new SubtractColorModifier();

        return Stream.of(color)
                .map(mapColor -> operationModifier.modify(mapColor, modifier.color.Color.RED, modifierValue))
                .map(mapColor -> operationModifier.modify(mapColor, modifier.color.Color.GREEN, modifierValue))
                .map(mapColor -> operationModifier.modify(mapColor, modifier.color.Color.BLUE, modifierValue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error while processing color chain!"));
    }
}
