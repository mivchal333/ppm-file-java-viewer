import file.Ppm3FileReader;
import file.model.PpmImage;
import view.image.ImagePanel;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class ApplicationFrame extends JFrame {

    private ImagePanel imagePanel;
    private Dialog dialog;


    public ApplicationFrame() {
        super("Grafika Komputerowa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1000, 1000);
        setVisible(true);

        prepareMenu();

        try {
            loadImage();
        } catch (Exception e) {
            e.printStackTrace();
            addErrorLabel("Error while file processing");
        }
    }

    private void addErrorLabel(String text) {
        JPanel errorPanel = new JPanel();
        errorPanel.setBackground(Color.RED);
        Label errorLabel = new Label(text);
        errorPanel.add(errorLabel);
        add(errorLabel);
    }

    private void loadImage() throws FileNotFoundException {
        Ppm3FileReader reader = new Ppm3FileReader();

//        String path = "ppm-test-01-p3.ppm";
        String path = "ppm-test-02-p3-comments.ppm";
//        String path = "ppm-test-04-p3-16bit.ppm";
//        String path = "ppm-test-07-p3-big.ppm";

        Scanner sc = new Scanner(new FileInputStream(path));

        PpmImage ppmImage = reader.convertImage(sc);

        Integer width = ppmImage.getWidth();
        Integer height = ppmImage.getHeight();
        BufferedImage image;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        ppmImage.getPixels()
                .forEach((i, pixel) -> {
                    Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB());
                    int x = i % width;
                    int y = i / width;
                    image.setRGB(x, y, color.getRGB());
                });

        imagePanel = new ImagePanel(image);
        add(imagePanel);
    }

    private void prepareMenu() {
        MenuBar mb = new MenuBar();
        Menu menu = new Menu("Menu");
        mb.add(menu);
        MenuItem i1 = new MenuItem("Save");
        i1.addActionListener(e -> showSaveDialog());
        menu.add(i1);

        MenuItem i2 = new MenuItem("Read");
        i2.addActionListener(e -> showReadDialog());
        menu.add(i2);

        setMenuBar(mb);
    }

    private void processReadImageAction(String filename) {
        try {
            BufferedImage image = ImageIO.read(new File(filename));
            imagePanel.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
            addErrorLabel("Error while reading");
        }
    }

    private void processSaveImageAction(String filename, Float compression) {
        BufferedImage image = imagePanel.getImage();
        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(compression);
        try {
            final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            writer.setOutput(new FileImageOutputStream(
                    new File(filename)));

            writer.write(null, new IIOImage(image, null, null), jpegParams);
        } catch (IOException ex) {
            ex.printStackTrace();
            addErrorLabel(ex.getMessage());
        }
    }

    private void showSaveDialog() {
        dialog = new Dialog(this, "Set save details", true);
        dialog.setLayout(new GridLayout());

        Button b = new Button("Go");
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> dialog.setVisible(false));

        TextField filenameField = new TextField("write.jpg");
        filenameField.setBounds(50, 50, 300, 20);

        TextField compressionField = new TextField("0.7");
        compressionField.setBounds(50, 100, 300, 20);

        b.addActionListener(e -> {
            dialog.setVisible(false);
            Float compression = Float.valueOf(compressionField.getText());
            processSaveImageAction(filenameField.getText(), compression);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dialog.add(b);
        dialog.add(filenameField);
        dialog.add(compressionField);
        dialog.add(closeButton);
        dialog.setSize(500, 500);
        dialog.setVisible(true);
    }

    private void showReadDialog() {
        dialog = new Dialog(this, "Read file", true);
        dialog.setLayout(new GridLayout());

        Button b = new Button("Go");
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> dialog.setVisible(false));

        TextField filenameField = new TextField("read.jpg");
        filenameField.setBounds(50, 50, 300, 20);

        b.addActionListener(e -> {
            dialog.setVisible(false);
            processReadImageAction(filenameField.getText());
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dialog.add(b);
        dialog.add(filenameField);
        dialog.add(closeButton);
        dialog.setSize(500, 500);
        dialog.setVisible(true);
    }
}