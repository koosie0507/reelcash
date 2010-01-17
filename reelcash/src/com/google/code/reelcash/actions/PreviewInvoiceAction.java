/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.actions;

import com.google.code.reelcash.data.DbManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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
        URL reportUrl = getClass().getResource("../reports/report2.jrxml");
        JOptionPane.showMessageDialog(null, reportUrl);

        try {
            String path = getClass().getResource("../reports/").getFile();
            String reportPath = reportUrl.getFile();
            HashMap params = new HashMap();
            params.put("INVOICEID", invoiceId); //TODO: put actual ID
            params.put("SUBREPORT_DIR", path);

            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("../reports/report2.jrxml"));
            JasperCompileManager.compileReportToFile(getClass().getResource("../reports/report2_subreport1.jrxml").getFile());
            JasperCompileManager.compileReportToFile(getClass().getResource("../reports/report2_subreport2.jrxml").getFile());

            DbManager mgr = new DbManager();
            JasperPrint print = JasperFillManager.fillReport(report, params, mgr.getConnection());
            JRViewer viewer = new JRViewer(print);
            JDialog dialog = new JDialog((JDialog)null, "Invoice", true);
            dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
            dialog.add(viewer);
            dialog.setMinimumSize(new Dimension(700, 500));
            dialog.pack();
            dialog.setVisible(true);
            //JasperDesignViewer.viewReportDesign(getClass().getResourceAsStream("../reports/report2.jrxml"), true);
        } catch (SQLException ex) {
            Logger.getLogger(PreviewInvoiceAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(PreviewInvoiceAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Throwable t) {
            JOptionPane.showMessageDialog(null, t);
        }
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
}
