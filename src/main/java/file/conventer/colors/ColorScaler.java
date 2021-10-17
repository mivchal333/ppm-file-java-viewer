package file.conventer.colors;

import file.model.Pixel;

public class ColorScaler {
    public static Pixel scaleColors(Pixel pixel, int bound) {
        return Pixel.builder()
                .r(scale(bound, pixel.getR()))
                .g(scale(bound, pixel.getG()))
                .b(scale(bound, pixel.getB()))
                .build();
    }

    private static int scale(int fileBound, int value) {
        double percentage = (double) value * 100 / (double) fileBound;
        return (int) percentage * 255 / 100;
    }
}
