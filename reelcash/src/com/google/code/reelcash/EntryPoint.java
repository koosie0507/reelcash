/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.DataRowTableModel;
import com.google.code.reelcash.data.DataRowTableModelDatabaseAdapter;
import com.google.code.reelcash.data.DbManager;
import com.google.code.reelcash.data.KeyRole;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.FlatLayoutNode;
import com.google.code.reelcash.data.layout.fields.BigDecimalField;
import com.google.code.reelcash.data.layout.fields.IntegerField;
import com.google.code.reelcash.data.layout.fields.StringField;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.util.ReportingUtils;
import com.google.code.reelcash.util.ScreenUtils;
import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

/**
 * <p>Class responsible for providing the point of entry in the application.</p>
 * 
 * @author andrei.olar
 */
public class EntryPoint {

    private static javax.swing.JFrame mainFrame;

    private static javax.swing.JFrame getMainFrame() {
        if (null == mainFrame) {
            mainFrame = new javax.swing.JFrame("Sunt o forma de cacat");
            mainFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            mainFrame.setLayout(new BorderLayout());
            ScreenUtils.computeMinimumSize(mainFrame);
            ScreenUtils.centerWindowOnScreen(mainFrame);
        }
        return mainFrame;
    }

    private static void showPopup(){
		JPopupMenu menu = new JPopupMenu();
		menu.setLightWeightPopupEnabled(true);
		JLabel label = new JLabel("Test test test");
		menu.add(label);
		menu.pack();
		menu.show(getMainFrame(), getMainFrame().getWidth()-20, getMainFrame().getHeight()-20);
	}

    /**
     * Entry point in the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
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
            getMainFrame().pack();
            getMainFrame().setVisible(true);
            showPopup();
        }
        catch (Throwable t) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Uncaught exception", t);
        }
    }
}
