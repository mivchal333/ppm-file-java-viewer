package modifier.binarization;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Binarization {

    public BufferedImage binarize(BufferedImage img, int threshold) {

        int h = img.getHeight();
        int w = img.getWidth();


        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {


                //Get RGB Value
                int val = img.getRGB(i, j);
                //Convert to three separate channels
                int r = (0x00ff0000 & val) >> 16;
                int g = (0x0000ff00 & val) >> 8;
                int b = (0x000000ff & val);
                int m = (r + g + b);
                if (m >= threshold) {
                    // for light color it set white
                    bufferedImage.setRGB(i, j, Color.WHITE.getRGB());
                } else {
                    // for dark color it will set black
                    bufferedImage.setRGB(i, j, 0);
                }
            }
        }
        return bufferedImage;
    }
}
