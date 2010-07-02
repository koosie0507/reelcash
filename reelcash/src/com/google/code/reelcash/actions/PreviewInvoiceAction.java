/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.actions;

import com.google.code.reelcash.Log;
import com.google.code.reelcash.data.DbManager;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.util.ReportingUtils;
import java.awt.event.ActionEvent;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;

/**
 *
 * @author cusi
 */
public class PreviewInvoiceAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private int invoiceId;
    private int accountId;

    public PreviewInvoiceAction(int invoiceId, int accountId) {
        this.invoiceId = invoiceId;
        this.accountId = accountId;
    }

    private void initializeAccount(DbManager mgr) {
        if (0 > accountId) {
            try {
                Statement stmt = mgr.getConnection().createStatement();
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery("select min(contactid) from contacts;");
                    if (!rs.next()) {
                        accountId = -1;
                    } else {
                        accountId = rs.getInt(1);
                    }
                } finally {
                    if (null != rs) {
                        rs.close();
                    }
                }
            } catch (SQLException e) {
                Log.write().warning(e.getMessage());
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            JasperReport report = ReportingUtils.loadReport("report2.jasper");
            DbManager mgr = new DbManager();
            initializeAccount(mgr);
            JasperPrint print = ReportingUtils.fillInvoice(
                    report, invoiceId, ReelcashDataSource.getInstance());
            ReportingUtils.showPreview(print);
        } catch (Throwable t) {
            Log.write().log(Level.SEVERE, "Can't show report!", t);
            JOptionPane.showMessageDialog(null, t);
        }
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
