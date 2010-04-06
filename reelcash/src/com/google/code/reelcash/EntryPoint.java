/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash;

import com.google.code.reelcash.data.DataRow;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * <p>Class responsible for providing the point of entry in the application.</p>
 * 
 * @author andrei.olar
 */
public class EntryPoint {

    /**
     * Entry point in the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            QueryMediator mediator = new QueryMediator(new ReelcashDataSource());
            DataLayoutNode node = new FlatLayoutNode("sample");
            node.getFieldSet().add(new IntegerField("id", KeyRole.PRIMARY, true));
            node.getFieldSet().add(new StringField("name", KeyRole.NONE, true));
            node.getFieldSet().add(new BigDecimalField("points", KeyRole.NONE, true));
            node.getFieldSet().add(new StringField("comments", KeyRole.NONE, false, 255));
            // mediator.createTable(node);
            DataRow row = new DataRow(node.getFieldSet());
            row.setValue("id", 1);
            row.setValue("name", "nume de test");
            row.setValue("points", 2.4552);
            row.setValue("comments", null);
            
            mediator.createRow(node.getName(), row);

            DbManager.checkCreateDb();
            ReportingUtils.compileReports();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MainFrame frm = new MainFrame();
            frm.pack();
            frm.setVisible(true);
        }
        catch (Throwable t) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Uncaught exception", t);
        }
    }
}
