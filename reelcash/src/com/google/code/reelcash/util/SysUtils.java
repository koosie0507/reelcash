/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.util;

import java.io.File;

/**
 * Class providing some patching to some of the jre's screwups.
 * @author cusi
 */
public class SysUtils {

    /**
     * Converts a java.util.Date to its java.sql cousin.
     * @param d date.
     * @return sql date.
     */
    public static java.sql.Date getSqlDate(java.util.Date d) {
        return new java.sql.Date(d.getTime());
    }

    public static String getHome() {
        return System.getProperty("user.home");
    }

    public static String getAppHome() {
        StringBuilder homeBuilder = new StringBuilder(System.getProperty("user.home"));
        homeBuilder.append(System.getProperty("file.separator"));
        homeBuilder.append(".reelcash");
        File home = new File(homeBuilder.toString());
        if (!home.exists()) {
            home.mkdir();
        }
        homeBuilder.append(System.getProperty("file.separator"));
        return homeBuilder.toString();
    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    public static String getNewLine() {
        return System.getProperty("line.separator");
    }

    /**
     * Gets the OS dependent file path separator (/ on linux).
     * @return the file separator
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }
}
