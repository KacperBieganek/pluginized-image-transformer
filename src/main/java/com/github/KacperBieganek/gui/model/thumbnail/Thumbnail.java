package com.github.KacperBieganek.gui.model.thumbnail;

import javax.swing.*;
import java.awt.*;

public class Thumbnail {
    ImageIcon image;

    public Thumbnail(ImageIcon image) {
        this.image = image;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}
