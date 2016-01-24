package ro.samlex.reelcash;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.io.CompanyDataStreamFactory;
import ro.samlex.reelcash.ui.MainWindow;
import ro.samlex.reelcash.ui.welcome.JWelcomeDialog;

public class Application {

    private static final Application APPLICATION_INSTANCE;

    public static void showMainFrame() {
        MainWindow window = new MainWindow();
        window.pack();
        window.setLocationByPlatform(true);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static void showWelcomeDialog() {
        JDialog welcomeDialog = new JWelcomeDialog(null, true);
        welcomeDialog.pack();
        welcomeDialog.setLocationByPlatform(true);
        welcomeDialog.setLocationRelativeTo(null);
        welcomeDialog.setVisible(true);
    }
    private final Party company;

    private Application() {
        company = new Party();
    }

    static {
        APPLICATION_INSTANCE = new Application();
    }

    public static Application getInstance() {
        return APPLICATION_INSTANCE;
    }

    public static void main(String[] arguments) {
        if (APPLICATION_INSTANCE.loadCompanyData()) {
            showMainFrame();
            return;
        }
        showWelcomeDialog();
    }
    
    public Party getCompany() {
        return company;
    }
    
    private boolean loadCompanyData() {
        try (InputStream is = new CompanyDataStreamFactory().createInputStream()) {
            company.load(is);
            return true;
        } catch (NullPointerException ex) {
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
