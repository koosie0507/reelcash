/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

/**
 *
 * @author andrei.olar
 */
public class ScreenUtils {

    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Point screenCenter = new Point((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2);

    public static Point getScreenCenter() {
        return screenCenter;
    }

    public static void centerWindowOnScreen(Window window) {
        int x = (int) (screenSize.getWidth() - window.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - window.getHeight()) / 2;
        window.setLocation(x, y);
    }

    public static void computeMinimumSize(Window window) {
        int width = (int) screenSize.getWidth() / 2;
        int height = (int) screenSize.getHeight() / 3;
        window.setMinimumSize(new Dimension(width, height));

    }
}
