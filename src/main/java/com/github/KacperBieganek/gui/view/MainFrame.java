package com.github.KacperBieganek.gui.view;

import javax.swing.*;

public class MainFrame extends JFrame {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    private JPanel mainPanel;
    private JButton loadPluginButton;
    private JButton executePluginButton;
    private JTable folderTable;

    public MainFrame() {
        super("pluginized-image-transformer");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public JButton getLoadPluginButton() {
        return loadPluginButton;
    }

    public JButton getExecutePluginButton() {
        return executePluginButton;
    }

    public JTable getFolderTable() {
        return folderTable;
    }
}
