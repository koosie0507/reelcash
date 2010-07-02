/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.util;

import com.google.code.reelcash.Log;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.sql.DataSource;
import javax.swing.JDialog;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 * This class provides wrapper methods for quick access to Jasper Report
 * functionalities.
 * @author cusi
 */
public final class ReportingUtils {

    private static final String invoiceReportUrl = "/com/google/code/reelcash/reports/invoice_simple.jrxml";
    private static final String invoiceSub1Url = "/com/google/code/reelcash/reports/report2_subreport1.jrxml";
    private static final String invoiceSub2Url = "/com/google/code/reelcash/reports/report2_subreport2.jrxml";
    private static final String reportsDir;
    private static JDialog dlg;
    public static final String SIMPLE_INVOICE_REPORT_NAME = "invoice_simple.jasper";

    static {
        StringBuilder builder = new StringBuilder(SysUtils.getAppHome());
        builder.append("reports");
        builder.append(SysUtils.getFileSeparator());
        reportsDir = builder.toString();
        File f = new File(reportsDir);
        if (!f.exists())
            f.mkdir();
    }

    private static String getReportPath(String reportName) {
        return reportsDir.concat(SysUtils.getFileSeparator()).concat(reportName);
    }

    private static boolean reportExists(String reportName) {
        File f = new File(getReportPath(reportName));
        return f.exists() && f.isFile();
    }

    private static boolean compileReport(String srcPath, String reportName) {
        if (!reportExists(reportName)) {
            FileOutputStream fos = null;
            try {
                InputStream is = ReportingUtils.class.getResourceAsStream(srcPath);
                fos = new FileOutputStream(getReportPath(reportName));
                JasperCompileManager.compileReportToStream(is, fos);
                return true;
            }
            catch (JRException ex) {
                Log.write().log(Level.WARNING, "Compile report error", ex);
            }
            catch (Throwable t) {
                Log.write().log(Level.SEVERE, "Compile report error", t);
            }
            finally {
                if (null != fos)
                    try {
                        fos.close();
                    }
                    catch (Throwable t) {
                        Log.write().log(Level.SEVERE, "Report save error", t);
                        return false;
                    }
            }
            return false;
        }
        return true;
    }

    private static JDialog getPreviewDialog(JasperPrint print) {
        Dimension maxSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension minSize = new Dimension((int) (maxSize.getWidth() / 1.5),
                (int) (maxSize.getHeight() / 1.5));
        Dimension prefSize = new Dimension(
                (int) (maxSize.getWidth() + minSize.getWidth()) / 2,
                (int) (maxSize.getHeight() + minSize.getHeight()) / 2);

        if (null == dlg) {
            dlg = new JDialog((Window) null, "Preview invoice", ModalityType.APPLICATION_MODAL);
            dlg.setMinimumSize(minSize);
            dlg.setMaximumSize(maxSize);
            dlg.setPreferredSize(prefSize);
            dlg.setLocation(new Point(
                    (int) (maxSize.getWidth() - prefSize.getWidth()) / 2,
                    (int) (maxSize.getHeight() - prefSize.getHeight()) / 2));
            dlg.setLayout(new BorderLayout());
        }
        dlg.getContentPane().removeAll();

        JRViewer view = new JRViewer(print);
        view.setMaximumSize(maxSize);
        view.setMinimumSize(minSize);
        view.setPreferredSize(prefSize);
        dlg.add(view, BorderLayout.CENTER);
        dlg.pack();
        return dlg;
    }

    public static boolean compileReports() {
        if (compileReport(invoiceReportUrl, "invoice_simple.jasper"))
            return true;
        return false;
    }

    public static JasperReport loadReport(String reportName) {
        try {
            return (JasperReport) JRLoader.loadObject(getReportPath(reportName));
        }
        catch (JRException ex) {
            Log.write().log(Level.SEVERE, "Can't load report", ex);
            return null;
        }
    }

    public static JasperPrint fillInvoice(JasperReport report, int invoiceId, DataSource dataSource) {
        try {
            HashMap map = new HashMap();
            map.put("INVOICEID", invoiceId);
            map.put("SUBREPORT_DIR", reportsDir);
            File f = new File(getReportPath("invoice_simple.jasper"));
            return JasperFillManager.fillReport(f.getPath(), map, dataSource.getConnection());
        }
        catch(SQLException ex) {
            Log.write().log(Level.SEVERE, "DB error on prepare report", ex);
        }
        catch (JRException ex) {
            Log.write().log(Level.SEVERE, "Reporting error", ex);
        }
        return null;
    }

    public static void showPreview(JasperPrint print) {
        try {
            getPreviewDialog(print).setVisible(true);
        }
        catch (Throwable t) {
            Log.write().log(Level.SEVERE, "Can't show preview", t);
        }
    }
}
