package com.github.KacperBieganek.gui.controller;

import com.github.KacperBieganek.gui.model.thumbnail.ThumbnailHolder;
import com.github.KacperBieganek.gui.view.MainFrame;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainFrameController {
    private final URL DIRECTORY_ICON_PATH = getClass().getClassLoader().getResource("appicons\\directory.png");
    private final URL FILE_ICON_PATH = getClass().getClassLoader().getResource("appicons\\file.png");
    private final ImageIcon directoryIcon = new ImageIcon(DIRECTORY_ICON_PATH);
    private final ImageIcon fileIcon = new ImageIcon(FILE_ICON_PATH);

    private MainFrame mainFrame;
    private ThumbnailHolder thumbnailHolder;
    private JTable folderTable;
    private JButton loadPluginButton;
    private JButton executePluginButton;
    private JList<String> pluginList;
    private DefaultTableModel tableModel;
    private File currentOpenDirectory;

    public MainFrameController() throws IOException, InterruptedException {
        initComponents();
        initListeners();
    }

    private void initComponents() throws IOException, InterruptedException {
        thumbnailHolder = new ThumbnailHolder();
        mainFrame = new MainFrame();

        folderTable = mainFrame.getFolderTable();
        loadPluginButton = mainFrame.getLoadPluginButton();
        executePluginButton = mainFrame.getExecutePluginButton();
        pluginList = mainFrame.getPluginList();

        setupTableModel();
        setupPluginList();

        mainFrame.setVisible(true);
    }

    private void initListeners() {
        folderTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    try {
                        File clickedFile = new File(currentOpenDirectory.getCanonicalPath() + File.separator + tableModel.getValueAt(row, 1));
                        if (clickedFile.isDirectory()) {
                            updateTableModel(clickedFile);
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(mainFrame, "File does not exists", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(mainFrame, "Thread got interrupted", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void setupTableModel() throws IOException, InterruptedException {
        final int THUMBNAIL_MAX_HEIGHT = 100;
        tableModel = new DefaultTableModel() {
            @Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        tableModel.setColumnCount(2);
        tableModel.setColumnIdentifiers(new String[]{"Thumbnail", "Path"});
        folderTable.setRowHeight(THUMBNAIL_MAX_HEIGHT);
        folderTable.setModel(tableModel);
        folderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        File directory = new File("./src/main/resources");
        updateTableModel(directory);
    }

    private void updateTableModel(@NotNull File directory) throws IOException, InterruptedException {
        clearTable();
        currentOpenDirectory = directory;
        tableModel.addRow(new Object[]{directoryIcon, ".."});
        for (File file : directory.listFiles()) {
            String path = file.getName();
            if (path.matches("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$")) {
                tableModel.addRow(new Object[]{thumbnailHolder.getThumbnail(file).getImage(), file.getName()});
            } else if (file.isDirectory()) {
                tableModel.addRow(new Object[]{directoryIcon, file.getName()});
            } else {
                tableModel.addRow(new Object[]{fileIcon, file.getName()});
            }
        }
        tableModel.fireTableDataChanged();
    }

    private void clearTable() {
        int rowCount = tableModel.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void setupPluginList(){
        DefaultListModel<String> pluginListModel = new DefaultListModel();
        pluginList.setModel(pluginListModel);
    }
}
