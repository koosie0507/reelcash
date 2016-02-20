package ro.samlex.reelcash.ui.components;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import ro.samlex.reelcash.data.Invoice;
import ro.samlex.reelcash.reporting.ReportFactory;
import ro.samlex.reelcash.ui.ApplicationMessages;

public class JReportPanel extends javax.swing.JPanel {

    public JReportPanel() {
        initComponents();
    }

    public void loadReport(Invoice invoice) {
        try {
            ReportFactory factory = new ReportFactory();
            JasperReport report = factory.createInvoiceReport();
            JasperPrint print = factory.createPrint(report, invoice);
            JRViewer viewer = new JRViewer(print) {
                @Override
                protected JRViewerToolbar createToolbar() {
                    JRViewerToolbar toolbar = super.createToolbar();
                    Locale locale = this.getLocale();
                    ResourceBundle bundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", locale);
                    JRSaveContributor[] saveFormats = { new JRPdfSaveContributor(locale, bundle) };
                    toolbar.setSaveContributors(saveFormats);
                    return toolbar;
                }
            };
            this.removeAll();
            this.add(viewer);
            this.revalidate();
        } catch (JRException | IOException ex) {
            ApplicationMessages.showError(this, "Can't show report preview: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(999999999, 999999999));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
