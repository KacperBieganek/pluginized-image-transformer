package com.github.KacperBieganek.engine.plugins;

import javafx.embed.swing.SwingFXUtils;
import sun.awt.image.ToolkitImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImageTransformPlugin {

    public abstract Image transform(Image image);

    protected BufferedImage toBufferedImage(Image image) {
        return ((ToolkitImage) image).getBufferedImage();
    }

    protected Image toImage(BufferedImage bufferedImage) {
        return (Image) bufferedImage;
    }

    public String getName() {
        return "Default Plugin Name";
    }
}
