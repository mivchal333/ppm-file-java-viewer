package view.image;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    public final double DEFAULT_SCALE = 10.0;

    private final double SCALE_CHANGE_DELTA = 0.4;

    @Getter
    private BufferedImage image;
    private double scale;


    public ImagePanel() {
        super();

        this.scale = DEFAULT_SCALE;
        this.addKeyListener(ResizeKeyListener.actions(() -> {
                    this.repaint();
                    scale += SCALE_CHANGE_DELTA;
                },
                () -> {
                    this.repaint();
                    scale -= SCALE_CHANGE_DELTA;
                }));

    }

    private void refreshPanel() {
        this.setFocusable(true);
        this.requestFocusInWindow();


        Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
        setPreferredSize(dimension);
        repaint();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        refreshPanel();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = (int) (image.getWidth() * scale);
        int height = (int) (image.getHeight() * scale);
        g2d.drawImage(image, 0, 0, width, height, this);
    }
}
