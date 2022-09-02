package org.example.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

enum Ground implements Icon {

    DIRT(new Color(205, 133, 63)), GRASS(new Color(0, 107, 60)),
    WATER(new Color(29, 172, 214)), CITY(Color.lightGray),
    ENEMY(Color.RED), HERO(Color.YELLOW);
    private static final int SIZE = 40;
    private Random random = new Random();
    private TexturePaint paint;

    private Ground(Color color) {
        this.paint = initPaint(color);
    }

    private TexturePaint initPaint(Color color) {
        BufferedImage image = new BufferedImage(
                SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, SIZE, SIZE);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (random.nextBoolean()) {
                    image.setRGB(col, row, color.getRGB());
                } else {
                    if (random.nextBoolean()) {
                        image.setRGB(col, row, color.darker().getRGB());
                    } else {
                        image.setRGB(col, row, color.brighter().getRGB());
                    }
                }
            }
        }
        return new TexturePaint(image, rect);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(paint);
        g.fillRect(0, 0, SIZE, SIZE);
    }

    @Override
    public int getIconWidth() {
        return SIZE;
    }

    @Override
    public int getIconHeight() {
        return SIZE;
    }
}