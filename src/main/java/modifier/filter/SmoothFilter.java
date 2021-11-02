package modifier.filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmoothFilter {
    private Color[] c;

    public BufferedImage process(BufferedImage input) {

        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

        int i = 0;
        int pixelInMaskCount = 100;
        int maskRadio = 5;
        Color[] colorsTemp = new Color[pixelInMaskCount];

        for (int x = 0; x < input.getHeight(); x++) {
            for (int y = 0; y < input.getWidth(); y++) {
                for (int tempX = x - maskRadio; tempX < x + maskRadio; tempX++) {
                    for (int tempY = y - maskRadio; tempY < y + maskRadio; tempY++) {
                        colorsTemp[i++] = new Color(getPixelRgb(tempY, tempX, input));
                    }
                }

                i = 0;
                int newR = 0, newG = 0, newB = 0;

                for (int d = 0; d < pixelInMaskCount; d++) {
                    newR = newR + colorsTemp[d].getRed();
                }

                newR = newR / (pixelInMaskCount);
                for (int d = 0; d < pixelInMaskCount; d++) {
                    newG = newG + colorsTemp[d].getGreen();
                }

                newG = newG / (pixelInMaskCount);
                for (int d = 0; d < pixelInMaskCount; d++) {
                    newB = newB + colorsTemp[d].getBlue();
                }

                newB = newB / (pixelInMaskCount);

                output.setRGB(y, x, getRgb(newR, newG, newB));
            }
        }
        return output;
    }

    private int getRgb(int r, int g, int b) {

        return (r << 16) + (g << 8) + b;
    }

    private int getPixelRgb(int y, int x, BufferedImage input) {
        try {
            return input.getRGB(y, x);
        } catch (Exception e) {
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            if (x > input.getHeight() - 1) {
                x = input.getHeight() - 1;
            }
            if (y > input.getWidth() - 1) {
                y = input.getWidth() - 1;
            }
            return input.getRGB(y, x);
        }
    }
}
