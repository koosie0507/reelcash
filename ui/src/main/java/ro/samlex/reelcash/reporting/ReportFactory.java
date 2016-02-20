package ro.samlex.reelcash.reporting;

import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import ro.samlex.reelcash.Application;

public class ReportFactory {

    private static final String INVOICE_REPORT_LOCATION = "/invoice.jasper";

    public final JasperReport createInvoiceReport() throws JRException {
        final URL reportUrl = Application.class.getResource(INVOICE_REPORT_LOCATION);
        JasperReport result = (JasperReport) JRLoader.loadObject(reportUrl);
        return result;
    }

    public final <T> JasperPrint createPrint(JasperReport report, T data) throws JRException, IOException {
        Gson serializer = new Gson();
        String json = serializer.toJson(data);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(json.getBytes())) {
            JsonDataSource dataSource = new JsonDataSource(bais);
            dataSource.setDatePattern("MMM d, yyyy hh:mm:ss aaa");
            HashMap parameters = new HashMap();
            parameters.put("SUBREPORT_DIR", "");
            return JasperFillManager.fillReport(report, parameters, dataSource);
        }
    }
}
