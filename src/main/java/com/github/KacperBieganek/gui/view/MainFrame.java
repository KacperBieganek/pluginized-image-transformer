package com.github.KacperBieganek.gui.view;

import javax.swing.*;

public class MainFrame extends JFrame{
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    private JPanel mainPanel;
    private JButton button1;
    private JButton button2;
    private JTree tree1;

    public MainFrame() {
        super("pluginized-image-transformer");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createUIComponents();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
