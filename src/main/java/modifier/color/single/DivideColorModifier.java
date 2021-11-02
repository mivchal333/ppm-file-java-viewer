package modifier.color.single;

public class DivideColorModifier extends OneColorModifier {
    @Override
    public int processPixel(int color, double value) {
        int result = (int) (color / value);
        return Math.max(result, MIN_COLOR_BOUND);
    }
}
