package com.google.code.reelcash;

/**
 * The enumeration provides the known permissions of the reelcash application. These permissions automatically fill the
 * DB permission table at startup. No use editing it.
 *
 * @author andrei.olar
 */
public enum Permission {

    /**
     * Represents the permission to register emitted documents with the current application.
     */
    EMIT('E', "emit"),
    /**
     * Represents the permission to register received documents with the current application.
     */
    RECEIVE('R', "receive");
    private char data;
    private String name;

    private Permission(char data, String name) {
        this.data = data;
        this.name = name;
    }

    /**
     * Returns the data of the permission.
     *
     * @return data.
     */
    public char getData() {
        return data;
    }

    /**
     * Returns the user-readable name of the permission.
     *
     * @return the name of a permission.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the character literal coresponding to a permission.
     *
     * @return the character literal of a permission.
     */
    @Override
    public String toString() {
        return String.valueOf(data);
    }
}
