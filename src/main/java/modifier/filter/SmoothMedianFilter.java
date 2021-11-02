package modifier.filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmoothMedianFilter {
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

                ArrayList<Integer> redColorValues = new ArrayList<>();
                ArrayList<Integer> greenColorValues = new ArrayList<>();
                ArrayList<Integer> blueColorValues = new ArrayList<>();
                for (int d = 0; d < pixelInMaskCount; d++) {
                    redColorValues.add(colorsTemp[d].getRed());
                    greenColorValues.add(colorsTemp[d].getGreen());
                    blueColorValues.add(colorsTemp[d].getBlue());
                }

                newR = getMedian(redColorValues);
                newG = getMedian(greenColorValues);
                newB = getMedian(blueColorValues);

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

    private int getMedian(List<Integer> values) {
        Collections.sort(values);
        int median;
        int size = values.size();
        if (size % 2 == 0)
            median = (values.get(size / 2) + values.get(size / 2 - 1)) / 2;
        else
            median = values.get(size / 2);
        return median;
    }
}
