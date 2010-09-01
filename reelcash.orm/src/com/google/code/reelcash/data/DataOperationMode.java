package com.google.code.reelcash.data;

/**
 * Enumerates the possible operation modes on data.
 * 
 * @author cusi
 */
public enum DataOperationMode {
    /**
     * The data is being created.
     */
    CREATE,

    /**
     * The data has been read from the database.
     */
    READ,

    /**
     * The data is being updated.
     */
    UPDATE,

    /**
     * The data is being deleted.
     */
    DELETE
}
