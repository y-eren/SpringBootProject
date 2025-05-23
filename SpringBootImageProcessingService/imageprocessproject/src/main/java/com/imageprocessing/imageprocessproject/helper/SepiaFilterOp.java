package com.imageprocessing.imageprocessproject.helper;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

public class SepiaFilterOp implements BufferedImageOp {

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = src.getRGB(x, y);

                int a = (rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                // Grayscale'e çevir
                int gray = (r + g + b) / 3;

                // Sepia tonları uygula
                r = Math.min(255, (int) (gray * 1.07));
                g = Math.min(255, (int) (gray * 0.74));
                b = Math.min(255, (int) (gray * 0.43));

                int newPixel = (a << 24) | (r << 16) | (g << 8) | b;
                output.setRGB(x, y, newPixel);
            }
        }

        return output;
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        return new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        return null;
    }


    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }

}
