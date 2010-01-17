/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * This is the application log. 
 * @author cusi
 */
public final class Log {

    private static Logger log;
    private static FileHandler fh;

    static {
        try {
            fh = new FileHandler("reelcash.log");
        } catch (IOException e) {
            fh = null;
        }
    }

    /**
     * Writes to a logger.
     * @return
     */
    public static Logger write() {
        synchronized (new Object()) {
            if (null == log) {
                log = Logger.getAnonymousLogger();
                if (null != fh) {
                    log.addHandler(fh);
                }
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
        synchronized (new Object()) {
            if (null == log) {
                log = Logger.getLogger(logName);
                if (null != fh) {
                    log.addHandler(fh);
                }
            }
        }
        return log;
    }
}
