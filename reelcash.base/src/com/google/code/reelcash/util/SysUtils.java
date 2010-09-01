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

	/**
	 * Returns the home directory of the current user.
	 * @return home directory of current user.
	 */
	public static String getHome() {
		return System.getProperty("user.home");
	}

	/**
	 * Returns the application's folder.
	 * 
	 * @return the home folder of the application
	 */
	public static String getAppHome() {
		StringBuilder homeBuilder = new StringBuilder(System.getProperty("user.home"));
		homeBuilder.append(System.getProperty("file.separator"));
		homeBuilder.append(".reelcash");
		File home = new File(homeBuilder.toString());
		if (!home.exists())
			home.mkdir();
		homeBuilder.append(System.getProperty("file.separator"));
		return homeBuilder.toString();
	}

	/**
	 * Returns the current working directory.
	 *
	 * @return the current user's working directory
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * Returns the character used to separate entries in the PATH environment variable
	 *
	 * @return ';' or ':' as of yet.
	 */
	public static String getPathSeparator() {
		return System.getProperty("path.separator");
	}

	/**
	 * Returns the platform specific sequence of characters which mark the end of a line of text.
	 *
	 * @return new line sequence
	 */
	public static String getNewLine() {
		return System.getProperty("line.separator");
	}

	/**
	 * Gets the OS dependent file path separator (/ on linux).
	 * 
	 * @return the file separator
	 */
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}

    public static java.sql.Date now() {
        return new java.sql.Date(java.util.Calendar.getInstance().getTimeInMillis());
    }
}
