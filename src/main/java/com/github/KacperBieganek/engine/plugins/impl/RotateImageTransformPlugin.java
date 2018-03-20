package com.github.KacperBieganek.engine.plugins.impl;

import com.github.KacperBieganek.engine.plugins.ImageTransformPlugin;
import sun.awt.image.ToolkitImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotateImageTransformPlugin extends ImageTransformPlugin {
    public static final String NAME = "Rotate 180";

    @Override
    public Image transform(Image image) {

        BufferedImage bfdImage = (BufferedImage) image;

        for (int i = 0; i < bfdImage.getWidth(); i++) {
            for (int j = 0; j < bfdImage.getHeight() / 2; j++) {
                int tmp = bfdImage.getRGB(i, j);
                bfdImage.setRGB(i, j, bfdImage.getRGB(i, bfdImage.getHeight() - j - 1));
                bfdImage.setRGB(i, bfdImage.getHeight() - j - 1, tmp);
            }
        }

        return bfdImage;
    }

    @Override
    public String getName() {
        return RotateImageTransformPlugin.NAME;
    }
}
