package modifier.color;

public class SubtractColorModifier extends OneColorModifier {
    @Override
    public int processPixel(int color, double value) {
        int subtract = (int) (color - value);
        return Math.max(subtract, MIN_COLOR_BOUND);
    }
}
