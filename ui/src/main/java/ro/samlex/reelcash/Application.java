package ro.samlex.reelcash;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.io.FileInputSource;
import ro.samlex.reelcash.ui.MainWindow;
import ro.samlex.reelcash.ui.welcome.JWelcomeDialog;

public class Application {

    private static final Application APPLICATION_INSTANCE;
    private final Path dbFolderPath;
    private Party company;

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

    private Application() {
        dbFolderPath = FileSystems.getDefault().getPath(SysUtils.getDbFolderPath());
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
        try {
            if (!Files.exists(dbFolderPath)) {
                Files.createDirectories(dbFolderPath);
            }
            Path companyDataFilePath = FileSystems.getDefault().getPath(
                    SysUtils.getDbFolderPath(), Reelcash.COMPANY_DATA_FILE_NAME);
            if (!Files.exists(companyDataFilePath)) {
                return false;
            }
            try (final Reader companyDataReader = new FileInputSource(companyDataFilePath).newReader()) {
                company = new Gson().fromJson(companyDataReader, Party.class);
            }
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
