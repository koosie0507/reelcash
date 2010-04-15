/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data;

import java.util.Comparator;

/**
 * <p>This comparator implementation will compare key roles such that the sorting order, from less to more,
 * should be NONE, KEY, UNIQUE, PRIMARY.</p>
 * @author andrei.olar
 */
public class KeyRoleComparator implements Comparator<KeyRole> {

    private static final Object syncRoot;
    private static KeyRoleComparator instance;

    static {
        syncRoot = new Object();
    }

    private KeyRoleComparator() {
    }

    /**
     * Returns the singleton instance of the comparator. This method is thread-safe.
     * @return a singleton instance of the comparator.
     */
    public static KeyRoleComparator getInstance() {
        synchronized (syncRoot) {
            if (null == instance)
                instance = new KeyRoleComparator();
        }
        return instance;
    }

    /**
     * Compares two key roles.
     * @param o1 the first key role.
     * @param o2 the second key role.
     * @return the third key role.
     */
    public int compare(KeyRole o1, KeyRole o2) {
        switch (o1) {
            case NONE:
                return KeyRole.NONE.equals(o2) ? 0 : -1; // normal columns are less than others
            case PRIMARY:
                return KeyRole.PRIMARY.equals(o2) ? 0 : 1; // primary key columns are greater than others
            case KEY:
                return KeyRole.NONE.equals(o2) // KEY columns are greater than normal columns
                        ? 1
                        : KeyRole.KEY.equals(o2) ? 0 : -1; // but less than UNIQUE and PRIMARY
            case UNIQUE:
                return KeyRole.PRIMARY.equals(o2) // UNIQUE columns are less than PRIMARY
                        ? -1
                        : KeyRole.UNIQUE.equals(o2) ? 0 : 1; // but greater than KEY and NONE

        }
        return 0; // indeterminate result for other key types
    }
}
