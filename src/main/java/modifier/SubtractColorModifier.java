package modifier;

public class SubtractColorModifier extends ColorModifier {
    @Override
    public int processPixel(int color, double value) {
        int subtract = (int) (color - value);
        return Math.max(subtract, MIN_COLOR_BOUND);
    }
}
