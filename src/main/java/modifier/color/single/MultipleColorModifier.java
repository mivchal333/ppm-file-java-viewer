package modifier.color.single;

public class MultipleColorModifier extends OneColorModifier {
    @Override
    public int processPixel(int color, double value) {
        int result = (int) (color * value);
        return Math.min(result, MAX_COLOR_BOUND);
    }
}
