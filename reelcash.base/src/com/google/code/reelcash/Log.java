/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * This is the application log. 
 * @author cusi
 */
public final class Log {

    private static final Object SYNC_ROOT = new Object();
    private static Logger log;
    private static FileHandler fh;

    static {
        try {
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            java.util.Date now = new java.util.Date(java.util.Calendar.getInstance().getTimeInMillis());
            fh = new FileHandler(String.format("reelcash_%s.log", format.format(now)));
        }
        catch (IOException e) {
            fh = null;
            e.printStackTrace(System.err);
        }
    }

    /**
     * Writes to a logger.
     * @return
     */
    public static Logger write() {
        synchronized (SYNC_ROOT) {
            if (null == log) {
                log = Logger.getLogger(Log.class.getPackage().getName());
                if (null != fh)
                    log.addHandler(fh);
            }
        }

        return log;
    }

    /**
     * logs to a logger
     * @param logName
     * @return
     */
    public static Logger write(String logName) {
        synchronized (SYNC_ROOT) {
            if (null == log) {
                log = Logger.getLogger(logName);
                if (null != fh)
                    log.addHandler(fh);
            }
        }
        return log;
    }
}
