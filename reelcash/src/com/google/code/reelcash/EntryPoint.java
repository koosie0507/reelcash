package com.google.code.reelcash;

import com.google.code.reelcash.data.DbManager;
import com.google.code.reelcash.swing.MainFrame;
import com.google.code.reelcash.util.ReportingUtils;
import com.google.code.reelcash.util.ScreenUtils;
import com.google.code.reelcash.util.SysUtils;
import java.util.logging.Level;
import javax.swing.UIManager;

/**
 * <p>Class responsible for providing the point of entry in the application.</p>
 * 
 * @author andrei.olar
 */
public class EntryPoint {

    private static com.google.code.reelcash.swing.JRegistryPanel panel;

    private static javax.swing.JFrame getMainFrame() {
        ScreenUtils.computeMinimumSize(MainFrame.getInstance());
        ScreenUtils.centerWindowOnScreen(MainFrame.getInstance());
        return MainFrame.getInstance();
    }

    /**
     * Entry point in the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String debug = SysUtils.getHome();
            ReportingUtils.compileReports();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            if (DbManager.checkCreateDb()) {
                getMainFrame().pack();
                getMainFrame().setVisible(true);
            }
        }
        catch (Throwable t) {
            Log.write().log(Level.SEVERE, "Uncaught exception", t);
        }
    }
}
