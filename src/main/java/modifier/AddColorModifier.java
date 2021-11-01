package modifier;

public class AddColorModifier extends ColorModifier {

    @Override
    public int processPixel(int color, double value) {
        int sum = (int) (color + value);
        return Math.max(sum, MAX_COLOR_BOUND);
    }
}
