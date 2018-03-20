package com.github.KacperBieganek;


import com.github.KacperBieganek.gui.controller.MainFrameController;
import com.github.KacperBieganek.gui.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException, InterruptedException {
       new MainFrameController();
    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new TestPane());
        frame.pack();
        frame.setVisible(true);
    }
}
class TestPane extends JPanel {
    DefaultTreeModel model;
    JTree tree;
    public TestPane() {
        setLayout(new BorderLayout());
        tree = new JTree();
        File rootFile = new File(".");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootFile);
        model = new DefaultTreeModel(root);

        tree.setModel(model);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);

        add(new JScrollPane(tree));

        JButton load = new JButton("Load");
        add(load, BorderLayout.SOUTH);

        load.addActionListener(e -> {
            DefaultMutableTreeNode r = (DefaultMutableTreeNode) model.getRoot();
            root.removeAllChildren();
            model.reload();
            File f = (File) r.getUserObject();
            addFiles(f, model, r);
            tree.expandPath(new TreePath(r));
        });
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
    protected void addFiles(File rootFile, DefaultTreeModel model,
                            DefaultMutableTreeNode root) {
        for (File file : rootFile.listFiles()) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
            model.insertNodeInto(child, root, root.getChildCount());
            if (file.isDirectory()) {
                addFiles(file, model, child);
            }
        }
    }
*/
}
