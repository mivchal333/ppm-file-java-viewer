package modifier.filter;


import java.awt.image.BufferedImage;

public class SobelFilter {
    public static int scaleToGrey(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
//        int gray = (r + g + b) / 3;

        return gray;
    }

    public BufferedImage process(BufferedImage input) {
        int x = input.getWidth();
        int y = input.getHeight();

        int[][] edgeColors = new int[x][y];
        int maxGradient = -1;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int val00 = scaleToGrey(input.getRGB(i - 1, j - 1));
                int val01 = scaleToGrey(input.getRGB(i - 1, j));
                int val02 = scaleToGrey(input.getRGB(i - 1, j + 1));

                int val10 = scaleToGrey(input.getRGB(i, j - 1));
                int val11 = scaleToGrey(input.getRGB(i, j));
                int val12 = scaleToGrey(input.getRGB(i, j + 1));

                int val20 = scaleToGrey(input.getRGB(i + 1, j - 1));
                int val21 = scaleToGrey(input.getRGB(i + 1, j));
                int val22 = scaleToGrey(input.getRGB(i + 1, j + 1));

                // o stopni
                int gx = ((-1 * val00) + (0 * val01) + (1 * val02))
                        + ((-2 * val10) + (0 * val11) + (2 * val12))
                        + ((-1 * val20) + (0 * val21) + (1 * val22));

                // 180 stopni
                int gy = ((-1 * val00) + (-2 * val01) + (-1 * val02))
                        + ((0 * val10) + (0 * val11) + (0 * val12))
                        + ((1 * val20) + (2 * val21) + (1 * val22));

                int g = (int) Math.sqrt((gx * gx) + (gy * gy));


                if (maxGradient < g) {
                    maxGradient = g;
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int) (edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                input.setRGB(i, j, edgeColor);
            }
        }

        return input;
    }
}
