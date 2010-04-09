/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash;

import com.google.code.reelcash.data.DataRowTableModel;
import com.google.code.reelcash.data.DataRowTableModelDatabaseAdapter;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import com.google.code.reelcash.data.layout.fields.Field;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;
import com.google.code.reelcash.swing.JRegistryPanel;
import com.google.code.reelcash.swing.FieldDisplay;
import com.google.code.reelcash.swing.FieldDisplayFactory;
import com.google.code.reelcash.util.ReportingUtils;
import com.google.code.reelcash.util.ScreenUtils;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

/**
 * <p>Class responsible for providing the point of entry in the application.</p>
 * 
 * @author andrei.olar
 */
public class EntryPoint {

    private static javax.swing.JFrame mainFrame;
    private static DataLayoutNode node;
    private static FieldDisplayFactory factory;

    private static DataLayoutNode getLayoutNode() {
        if (null == node) {
            node = new RootLayoutNode("countries");
            node.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
            node.getFieldList().add(new StringField("name", KeyRole.NONE, true));
            node.getFieldList().add(new StringField("iso_name", KeyRole.NONE, true));
            node.getFieldList().add(new StringField("iso_code2", KeyRole.NONE, true));
            node.getFieldList().add(new StringField("iso_code3", KeyRole.NONE, true));
        }
        return node;
    }

    private static FieldDisplayFactory getFactory() {
        if (null == factory)
            factory = new FieldDisplayFactory() {

                {
                    for (Field f : getLayoutNode()) {
                        FieldDisplay dispInfo = FieldDisplay.newInstance(f);
                        super.getData().put(f, dispInfo);
                        dispInfo.setCaption(dispInfo.getCaption().toUpperCase());
                        if ("id".equals(f.getName()))
                            dispInfo.setVisible(false);
                    }
                }

                @Override
                public FieldDisplay getUIDisplayInfo(Field field) {
                    return getData().get(field);
                }
            };
        return factory;
    }

    private static javax.swing.JFrame getMainFrame() {
        if (null == mainFrame) {
            mainFrame = new javax.swing.JFrame("Sunt o forma de cacat");
            mainFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            mainFrame.setLayout(new BorderLayout());
            ScreenUtils.computeMinimumSize(mainFrame);
            ScreenUtils.centerWindowOnScreen(mainFrame);

            JRegistryPanel panel = new JRegistryPanel() {

                @Override
                public DataLayoutNode getDataLayoutNode() {
                    return getLayoutNode();
                }

                @Override
                public FieldDisplayFactory getDisplayInfoFactory() {
                    return getFactory();
                }
            };
            panel.setCaption("Countries");
            panel.getDatabaseAdapter().readAll();
            mainFrame.add(panel, BorderLayout.CENTER);
        }
        return mainFrame;
    }

    /**
     * Entry point in the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            /*
            DataLayoutNode node = new FlatLayoutNode("countries");
            DataRowTableModel model = new DataRowTableModel(node);
            DataRowTableModelDatabaseAdapter adapter = new DataRowTableModelDatabaseAdapter(ReelcashDataSource.getInstance(), model);
            node.getFieldList().add(new IntegerField("id", KeyRole.PRIMARY, true));
            node.getFieldList().add(new StringField("name", KeyRole.NONE, true));
            node.getFieldList().add(new StringField("iso_name", KeyRole.NONE, true));
            node.getFieldList().add(new StringField("iso_code2", KeyRole.NONE, true));
            node.getFieldList().add(new StringField("iso_code3", KeyRole.NONE, true));
            adapter.readAll();


            JScrollPane pane = new JScrollPane();
            JTable table = new JTable(model);
            table.getTableHeader().setVisible(true);
            pane.setViewportView(table);
            getMainFrame().add(pane);

            ReportingUtils.compileReports();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
             */
            getMainFrame().pack();
            getMainFrame().setVisible(true);
        }
        catch (Throwable t) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Uncaught exception", t);
        }
    }
}
