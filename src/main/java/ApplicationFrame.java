import file.Ppm3FileReader;
import file.model.PpmImage;
import view.image.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ApplicationFrame extends JFrame {

    public ApplicationFrame() {
        super("Grafika Komputerowa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1000, 1000);
        setVisible(true);


        Ppm3FileReader reader = new Ppm3FileReader();

        String path = "ppm-test-01-p3.ppm";
//        String path = "ppm-test-02-p3-comments.ppm";
//        String path = "ppm-test-04-p3-16bit.ppm";
//        String path = "ppm-test-07-p3-big.ppm";
        try {
            Scanner sc = new Scanner(new FileInputStream(path));

            PpmImage ppmImage = reader.convertImage(sc);

            Integer width = ppmImage.getWidth();
            Integer height = ppmImage.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


            ppmImage.getPixels()
                    .forEach((i, pixel) -> {
                        Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB());
                        int x = i % width;
                        int y = i / width;
                        image.setRGB(x, y, color.getRGB());
                    });

            JPanel imagePanel = new ImagePanel(image);
            add(imagePanel);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}