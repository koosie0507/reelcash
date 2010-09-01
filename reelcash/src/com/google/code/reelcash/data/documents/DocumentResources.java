package com.google.code.reelcash.data.documents;

import java.util.ResourceBundle;

/**
 * Provides regionalized strings in the context of model fields.
 * 
 * @author andrei.olar
 */
public class DocumentResources {

    private static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle(DocumentResources.class.getPackage().getName().concat(".document"));
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
