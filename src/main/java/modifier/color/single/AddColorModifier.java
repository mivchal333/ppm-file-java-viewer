package modifier.color.single;

public class AddColorModifier extends OneColorModifier {

    @Override
    public int processPixel(int color, double value) {
        int sum = (int) (color + value);
        return Math.min(sum, MAX_COLOR_BOUND);
    }
}
