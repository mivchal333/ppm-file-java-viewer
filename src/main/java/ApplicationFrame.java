import file.Ppm3FileReader;
import file.model.PpmImage;
import modifier.color.*;
import modifier.color.single.AddColorModifier;
import modifier.color.single.DivideColorModifier;
import modifier.color.single.MultipleColorModifier;
import modifier.color.single.SubtractColorModifier;
import modifier.filter.SmoothAvgFilter;
import modifier.filter.SmoothMedianFilter;
import modifier.filter.SobelFilter;
import modifier.histogram.HistogramEqualization;
import view.image.ColorSelectExtractor;
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

    private final ImagePanel imagePanel = new ImagePanel();
    private Dialog dialog;
    private ColorModifier colorModifier;
    private modifier.color.single.Color modifierColor;
    private double modifierValue;
    private PpmImage ppmImage;

    public ApplicationFrame() {
        super("Grafika Komputerowa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1000, 1000);
        setVisible(true);

        add(imagePanel);

        prepareMenu();

        try {
            ppmImage = readImage("ppm-test-02-p3-comments.ppm");
            loadImage();

        } catch (Exception e) {
            e.printStackTrace();
            addErrorLabel("Error while file processing");
        }
    }

    private void addErrorLabel(String text) {
        remove(imagePanel);
        repaint();
        revalidate();
        JPanel errorPanel = new JPanel();
        errorPanel.setBackground(Color.RED);
        Label errorLabel = new Label(text);
        errorPanel.add(errorLabel);
        add(errorLabel);
        repaint();
        revalidate();
    }

    private PpmImage readImage(String path) throws FileNotFoundException {
        Ppm3FileReader reader = new Ppm3FileReader();

        Scanner sc = new Scanner(new FileInputStream(path));

        return reader.convertImage(sc);
    }

    private void loadImage() {
        Integer width = ppmImage.getWidth();
        Integer height = ppmImage.getHeight();
        BufferedImage image;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        ppmImage.getPixels()
                .forEach((i, pixel) -> {
                    Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB());
                    if (colorModifier != null) {
                        color = colorModifier.modify(color, modifierColor, modifierValue);
                    }
                    int x = i % width;
                    int y = i / width;
                    image.setRGB(x, y, color.getRGB());
                });

        imagePanel.setImage(image);
    }

    private void prepareMenu() {
        MenuBar mb = new MenuBar();
        Menu menu = new Menu("Menu");
        mb.add(menu);
        MenuItem i1 = new MenuItem("Save");
        i1.addActionListener(e -> showSaveDialog());
        menu.add(i1);

        MenuItem i2 = new MenuItem("Read JPG");
        i2.addActionListener(e -> showReadDialog());
        menu.add(i2);

        MenuItem i3 = new MenuItem("Read PPM");
        i3.addActionListener(e -> showReadPpmDialog());
        menu.add(i3);


        Menu colors = new Menu("Colors");
        mb.add(colors);

        MenuItem addColor = new MenuItem("Add");
        addColor.addActionListener(e -> {
            colorModifier = new AddColorModifier();
            showColorModifierDialog();
        });
        colors.add(addColor);

        MenuItem subtract = new MenuItem("Subtract");
        subtract.addActionListener(e -> {
            colorModifier = new SubtractColorModifier();
            showColorModifierDialog();
        });
        colors.add(subtract);

        MenuItem multiple = new MenuItem("Multiple");
        multiple.addActionListener(e -> {
            colorModifier = new MultipleColorModifier();
            showColorModifierDialog();
        });
        colors.add(multiple);
        MenuItem divide = new MenuItem("Divide");
        divide.addActionListener(e -> {
            colorModifier = new DivideColorModifier();
            showColorModifierDialog();
        });
        colors.add(divide);

        MenuItem brightness = new MenuItem("Brightness");
        brightness.addActionListener(e -> {
            showBrightnessModifierDialog();
        });
        colors.add(brightness);

        MenuItem toGrey = new MenuItem("To Grey Avg");
        toGrey.addActionListener(e -> {
            colorModifier = new GreyAvgModifier();
            loadImage();
        });
        colors.add(toGrey);

        MenuItem toGreyWeightAvg = new MenuItem("To Grey Weight Avg");
        toGreyWeightAvg.addActionListener(e -> {
            colorModifier = new GreyWeightAvgModifier();
            loadImage();
        });
        colors.add(toGreyWeightAvg);


        Menu filters = new Menu("Filters");
        mb.add(filters);

        MenuItem smoothFilter = new MenuItem("Smooth Avg Filter");
        smoothFilter.addActionListener(e -> {
            processSmoothAvgFilter();
        });
        filters.add(smoothFilter);

        MenuItem medianFilter = new MenuItem("Smooth Median Filter");
        medianFilter.addActionListener(e -> {
            processSmoothMedianFilter();
        });
        filters.add(medianFilter);

        MenuItem sobelFilter = new MenuItem("Sobel Filter");
        sobelFilter.addActionListener(e -> {
            processSobelFilter();
        });
        filters.add(sobelFilter);


        Menu histAndBin = new Menu("Hist & Bin");
        mb.add(histAndBin);

        MenuItem histogramEqualisation = new MenuItem("Histogram Equalisation");
        histogramEqualisation.addActionListener(e -> {
            processHistogramEqualisation();
        });
        histAndBin.add(histogramEqualisation);


        setMenuBar(mb);
    }

    private void processHistogramEqualisation() {
        BufferedImage processedImage = new HistogramEqualization().histogramEqualization(imagePanel.getImage());
        imagePanel.setImage(processedImage);
    }

    void processSmoothMedianFilter() {
        SmoothMedianFilter filter = new SmoothMedianFilter();
        BufferedImage processed = filter.process(imagePanel.getImage());
        imagePanel.setImage(processed);
    }

    void processSobelFilter() {
        SobelFilter filter = new SobelFilter();
        BufferedImage processed = filter.process(imagePanel.getImage());
        imagePanel.setImage(processed);
    }

    void processSmoothAvgFilter() {
        SmoothAvgFilter filter = new SmoothAvgFilter();
        BufferedImage processed = filter.process(imagePanel.getImage());
        imagePanel.setImage(processed);
    }

    private void showReadPpmDialog() {
        dialog = new Dialog(this, "Read PPM", true);
        dialog.setLayout(new GridLayout());

        Button b = new Button("Go");
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> dialog.setVisible(false));

        TextField filenameField = new TextField("ppm-test-02-p3-comments.ppm");
        filenameField.setBounds(50, 50, 300, 20);

        b.addActionListener(e -> {
            dialog.setVisible(false);
            try {
                ppmImage = readImage(filenameField.getText());
                loadImage();
            } catch (Exception ex) {
                ex.printStackTrace();
                addErrorLabel("File load error" + ex.getMessage());
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dialog.add(b);
        dialog.add(filenameField);
        dialog.add(closeButton);
        dialog.setSize(500, 500);
        dialog.setVisible(true);
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

    private void showColorModifierDialog() {
        dialog = new Dialog(this, "Select Color", true);
        dialog.setLayout(new GridLayout());

        Button b = new Button("Go");
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> dialog.setVisible(false));

        TextField colorField = new TextField("red");
        colorField.setBounds(50, 50, 300, 20);

        TextField valueTextField = new TextField("10.0");
        valueTextField.setBounds(50, 50, 300, 20);

        b.addActionListener(e -> {
            dialog.setVisible(false);
            modifierValue = Double.parseDouble(valueTextField.getText());
            modifierColor = ColorSelectExtractor.parse(colorField.getText());
            loadImage();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dialog.add(b);
        dialog.add(colorField);
        dialog.add(valueTextField);
        dialog.add(closeButton);
        dialog.setSize(500, 500);
        dialog.setVisible(true);
    }

    private void showBrightnessModifierDialog() {
        dialog = new Dialog(this, "Select Brightness", true);
        dialog.setLayout(new GridLayout());

        Button b = new Button("Go");
        Button closeButton = new Button("Close");
        closeButton.addActionListener(e -> dialog.setVisible(false));

        TextField operationTypeField = new TextField("add");
        operationTypeField.setBounds(50, 50, 300, 20);

        TextField valueTextField = new TextField("10.0");
        valueTextField.setBounds(50, 50, 300, 20);

        b.addActionListener(e -> {
            dialog.setVisible(false);
            if ("add".equals(operationTypeField.getText())) {
                colorModifier = new AddBrightnessModifier();
                return;
            } else {
                colorModifier = new SubtractBrightnessModifier();
            }
            modifierValue = Double.parseDouble(valueTextField.getText());
            loadImage();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dialog.add(b);
        dialog.add(operationTypeField);
        dialog.add(valueTextField);
        dialog.add(closeButton);
        dialog.setSize(500, 500);
        dialog.setVisible(true);
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