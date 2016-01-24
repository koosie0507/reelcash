package ro.samlex.reelcash.ui;

import ro.samlex.reelcash.Reelcash;

import javax.swing.*;
import java.awt.*;

public class ApplicationMessages {
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                Reelcash.APPLICATION_FRIENDLY_NAME + " has encountered an error",
                JOptionPane.ERROR_MESSAGE);
    }
}
