package modifier.filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmoothFilter {
    private Color[] c;

    public BufferedImage process(BufferedImage input) {
        Color[] color;

        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

        int i = 0;
        int max = 400, rad = 10;
        int r1 = 0, g1 = 0, b1 = 0;
        color = new Color[max];

        // Now this core section of code is responsible for
        // blurring of an image

        int x, y, x1, y1, d;

        // Running nested for loops for each pixel
        // and blurring it
        for (x = rad; x < input.getHeight() - rad; x++) {
            for (y = rad; y < input.getWidth() - rad; y++) {
                for (x1 = x - rad; x1 < x + rad; x1++) {
                    for (y1 = y - rad; y1 < y + rad; y1++) {
                        color[i++] = new Color(input.getRGB(y1, x1));
                    }
                }

                i = 0;

                for (d = 0; d < max; d++) {
                    r1 = r1 + color[d].getRed();
                }

                r1 = r1 / (max);
                for (d = 0; d < max; d++) {
                    g1 = g1 + color[d].getGreen();
                }

                g1 = g1 / (max);
                for (d = 0; d < max; d++) {
                    b1 = b1 + color[d].getBlue();
                }

                b1 = b1 / (max);
                int sum1 = (r1 << 16) + (g1 << 8) + b1;
                output.setRGB(y, x, sum1);
            }
        }
        return output;
    }

    public BufferedImage process2(BufferedImage input) {
        Color[] color;

        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = input.getRGB(x, y);
            }
        }
        return output;
    }

    private int getRgb(int x, int y, BufferedImage input) {
        try {
            return input.getRGB(x, y);
        } catch (Exception e) {
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            if (x > input.getWidth() - 1) {
                x = input.getWidth() - 1;
            }
            if (y > input.getHeight() - 1) {
                y = input.getHeight() - 1;
            }
            return input.getRGB(x, y);
        }
    }
}
