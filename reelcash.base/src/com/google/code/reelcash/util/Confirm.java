package com.google.code.reelcash.util;

import javax.swing.JOptionPane;

/**
 * Insert some documentation for the class <b>Confirm</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public final class Confirm {

    private static int displayMessage(String msgFormat, int type, Object... params) {
        if (params.length < 1)
            return JOptionPane.showConfirmDialog(null, msgFormat, "Reelcash message", type);
        else
            return JOptionPane.showConfirmDialog(null, String.format(msgFormat, params), "Reelcash message", type);
    }

    public static int confirm(String message) {
        return displayMessage(message, JOptionPane.YES_NO_OPTION);
    }

    public static int confirm(String format, Object... params) {
        return displayMessage(format, JOptionPane.YES_NO_OPTION, params);
    }
}
