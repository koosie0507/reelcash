package com.google.code.reelcash;

import com.google.code.reelcash.swing.*;
import com.google.code.reelcash.data.layout.fields.*;
import java.util.ResourceBundle;

/**
 * Provides regionalized strings in the context of model fields.
 * 
 * @author andrei.olar
 */
public class GlobalResources {

    private static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle(GlobalResources.class.getPackage().getName().concat(".global"));
        if (null == bundle)
            throw new RuntimeException("FATAL: can't initialize field resources!");
    }

    /**
     * Retrieves a string from the resource bundle.
     * @param key a key by which we find the string.
     * @return the value of the string in the current culture.
     */
    public static String getString(String key) {
        return bundle.getString(key);
    }
}
