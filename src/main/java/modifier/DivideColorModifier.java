package modifier;

public class DivideColorModifier extends ColorModifier {
    @Override
    public int processPixel(int color, double value) {
        int result = (int) (color / value);
        return Math.max(result, MIN_COLOR_BOUND);
    }
}
