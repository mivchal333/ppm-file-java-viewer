package view.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    public final int DEFAULT_SCALE = 5;

    private final int SCALE_CHANGE_DELTA = 1;

    private final BufferedImage image;
    private int scale;

    public ImagePanel(BufferedImage image) {
        super();

        this.image = image;
        this.scale = DEFAULT_SCALE;
        this.addKeyListener(ResizeKeyListener.actions(() -> {
                    this.repaint();
                    scale += SCALE_CHANGE_DELTA;
                },
                () -> {
                    this.repaint();
                    scale -= SCALE_CHANGE_DELTA;
                }));

        this.setFocusable(true);
        this.requestFocusInWindow();


        Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
        setPreferredSize(dimension);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = image.getWidth() * scale;
        int height = image.getHeight() * scale;
        g2d.drawImage(image, 0, 0, width, height, this);
    }
}
