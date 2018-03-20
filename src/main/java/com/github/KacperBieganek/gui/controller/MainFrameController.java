package com.github.KacperBieganek.gui.controller;

import com.github.KacperBieganek.gui.model.thumbnail.ThumbnailHolder;
import com.github.KacperBieganek.gui.view.MainFrame;
import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainFrameController {

    private final int THUMBNAIL_MAX_HEIGHT = 100;
    private final URL DIRECTORY_ICON_PATH =  getClass().getClassLoader().getResource("appicons\\directory.png");
    private final URL FILE_ICON_PATH =  getClass().getClassLoader().getResource("appicons\\file.png");

    private final ImageIcon directoryIcon = new ImageIcon(DIRECTORY_ICON_PATH);
    private final ImageIcon fileIcon = new ImageIcon(FILE_ICON_PATH);

    MainFrame mainFrame;
    ThumbnailHolder thumbnailHolder;
    JTable folderTable;
    JButton loadPluginButton;
    JButton executePluginButton;
    DefaultTableModel model;
    File currentOpenDirectory;

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

        setupTableModel();

        mainFrame.setVisible(true);
    }


    private void initListeners() {
        folderTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    try {
                        File clickedFile = new File(currentOpenDirectory.getCanonicalPath()+"/"+ model.getValueAt(row,1));
                        if(clickedFile.isDirectory()) {
                            updateTableModel(clickedFile);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setupTableModel() throws IOException, InterruptedException {
        model = new DefaultTableModel() {
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
        model.setColumnCount(2);
        model.setColumnIdentifiers(new String[]{"Thumbnail", "Path"});
        folderTable.setRowHeight(THUMBNAIL_MAX_HEIGHT);
        folderTable.setModel(model);
        folderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        File directory = new File("./src/main/resources");
        updateTableModel(directory);
    }

    private void updateTableModel(File directory) throws IOException, InterruptedException {
        clearTable();
        currentOpenDirectory = directory;
        model.addRow(new Object[]{directoryIcon,".."});
        for (File file : directory.listFiles()) {
            String path = file.getName();
             if( path.matches("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$")){
                model.addRow(new Object[]{thumbnailHolder.getThumbnail(file).getImage(), file.getName()});
            } else if (file.isDirectory()) {
                model.addRow(new Object[]{directoryIcon,file.getName()});
            } else {
                model.addRow(new Object[]{fileIcon, file.getName()});
            }
        }
        model.fireTableDataChanged();
    }

    private void clearTable() {
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
}
