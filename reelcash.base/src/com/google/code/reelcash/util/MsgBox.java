package com.google.code.reelcash.util;

import javax.swing.JOptionPane;

/**
 * Insert some documentation for the class <b>MsgBox</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public final class MsgBox {

    private static void displayMessage(String msgFormat, int type, Object... params) {
        if (params.length < 1)
            JOptionPane.showMessageDialog(null, msgFormat, "Reelcash message", type);
        else
            JOptionPane.showMessageDialog(null, String.format(msgFormat, params), "Reelcash message", type);
    }

    public static void info(String message) {
        displayMessage(message, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void infoFormat(String message, Object... params) {
        displayMessage(message, JOptionPane.INFORMATION_MESSAGE, params);
    }

    public static void text(String message) {
        displayMessage(message, JOptionPane.PLAIN_MESSAGE);
    }

    public static void textFormat(String message, Object... params) {
        displayMessage(message, JOptionPane.PLAIN_MESSAGE, params);
    }

    public static void warn(String message) {
        displayMessage(message, JOptionPane.WARNING_MESSAGE);
    }

    public static void warnFormat(String message, Object... params) {
        displayMessage(message, JOptionPane.WARNING_MESSAGE, params);
    }

    public static void error(String message) {
        displayMessage(message, JOptionPane.ERROR_MESSAGE);
    }

    public static void errorFormat(String message, Object... params) {
        displayMessage(message, JOptionPane.ERROR_MESSAGE, params);
    }

    public static void exception(Throwable t) {
        displayMessage("%s\n%s", JOptionPane.INFORMATION_MESSAGE, t.getMessage(), t.getStackTrace());
    }
}
