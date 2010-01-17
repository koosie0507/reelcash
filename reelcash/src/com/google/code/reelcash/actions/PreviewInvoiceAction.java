/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.actions;

import com.google.code.reelcash.Log;
import com.google.code.reelcash.data.DbManager;
import com.google.code.reelcash.util.ReportingUtils;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.*;

/**
 *
 * @author cusi
 */
public class PreviewInvoiceAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private int invoiceId;

    public PreviewInvoiceAction(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            JasperReport report = ReportingUtils.loadReport("report2.jasper");
            DbManager mgr = new DbManager();
            JasperPrint print = ReportingUtils.fillInvoice(
                    report, invoiceId, mgr.getConnection());
            ReportingUtils.showPreview(print);
        } catch (Throwable t) {
            Log.write().log(Level.SEVERE, "Can't show report!", t);
            JOptionPane.showMessageDialog(null, t);
        }
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
}
