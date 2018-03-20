package com.github.KacperBieganek.gui.controller;

import com.github.KacperBieganek.engine.PluginLoader;
import com.github.KacperBieganek.gui.model.thumbnail.ThumbnailHolder;
import com.github.KacperBieganek.gui.model.thumbnail.ThumbnailLoader;
import com.github.KacperBieganek.gui.view.MainFrame;
import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private JButton executeMethodButton;
    private JList<String> pluginList;
    private JList<String> methodList;
    private JLabel pluginLabel;
    private DefaultTableModel tableModel;
    private File currentOpenDirectory;
    private Class loadedClass;
    private Method[] methods;

    public MainFrameController() throws IOException, InterruptedException {
        initComponents();
        initListeners();
    }

    private void initComponents() throws IOException, InterruptedException {
        thumbnailHolder = new ThumbnailHolder();
        mainFrame = new MainFrame();

        folderTable = mainFrame.getFolderTable();
        loadPluginButton = mainFrame.getLoadPluginButton();
        executeMethodButton = mainFrame.getExecuteMethodButton();
        pluginList = mainFrame.getPluginList();
        pluginLabel = mainFrame.getPluginLabel();
        methodList = mainFrame.getMethodList();

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

        loadPluginButton.addActionListener(al -> {
            PluginLoader pluginLoader = new PluginLoader(this.getClass().getClassLoader());
            DefaultListModel<String> methodsListModel = new DefaultListModel<>();
            try {
                loadedClass = pluginLoader.loadClass(pluginList.getSelectedValue());
                pluginLabel.setText(pluginList.getSelectedValue());
                methods = loadedClass.getDeclaredMethods();
                for (Method method : methods) {
                    methodsListModel.addElement(method.getName());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            methodList.setModel(methodsListModel);
        });

        executeMethodButton.addActionListener(al -> {
            final int pathColumn = 1;
            String path = currentOpenDirectory + "/" + folderTable.getValueAt(folderTable.getSelectedRow(), pathColumn);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(mainFrame, "File is missing", "Error", JOptionPane.ERROR_MESSAGE);
            }
            try {
                int index = methodList.getSelectedIndex();
                Method selectedMethod = methods[index];
                Object o = loadedClass.newInstance();
                if (selectedMethod.getReturnType().getName().startsWith("[")) {
                    Image result = (Image) selectedMethod.invoke(o, image);
                    JOptionPane.showConfirmDialog(null, "Transformed");
                } else {
                    Image result = (Image) selectedMethod.invoke(o, image);
                    ImageIO.write((BufferedImage)result, "png", new File(path));
                    tableModel.fireTableDataChanged();
                }
            } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
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

    private void setupPluginList() {
        DefaultListModel<String> pluginListModel = new DefaultListModel();
        File pluginDir = new File(".\\out\\production\\classes\\com\\github\\KacperBieganek\\engine\\plugins\\impl");
        for (File file : pluginDir.listFiles()) {
            pluginListModel.addElement(file.getName());
        }
        pluginList.setModel(pluginListModel);
    }
}
