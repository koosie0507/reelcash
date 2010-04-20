package com.google.code.reelcash;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.DbManager;
import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.swing.JRegistriesPanel;
import com.google.code.reelcash.util.ScreenUtils;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Class responsible for providing the point of entry in the application.</p>
 * 
 * @author andrei.olar
 */
public class EntryPoint {

    private static javax.swing.JFrame mainFrame;
    private static com.google.code.reelcash.swing.JRegistryPanel panel;

    private static javax.swing.JFrame getMainFrame() {
        if (null == mainFrame) {
            mainFrame = new javax.swing.JFrame("Test Frame");
            mainFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            mainFrame.setLayout(new BorderLayout());
            ScreenUtils.computeMinimumSize(mainFrame);
            ScreenUtils.centerWindowOnScreen(mainFrame);
            mainFrame.add(JRegistriesPanel.getInstance(), BorderLayout.CENTER);
        }
        return mainFrame;
    }

    /**
     * Entry point in the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            if (DbManager.checkCreateDb()) {
                QueryMediator mediator = new QueryMediator(ReelcashDataSource.getInstance());
                DataRow[] data = mediator.fetch("SELECT * FROM banks where id=?;",1);
                getMainFrame().pack();
                getMainFrame().setVisible(true);
            }
        }
        catch (Throwable t) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Uncaught exception", t);
        }
    }
}
