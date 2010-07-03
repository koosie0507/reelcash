package com.google.code.reelcash.data;

import java.util.Comparator;

/**
 * Compares layout hierarchy values. NONE is followed by ALLOW CHILD, by ALL and finally ALLOW PARENT.
 *
 * @author andrei.olar
 */
public class LayoutHierarchyPermissionComparator implements Comparator<LayoutHierarchyPermission> {

    private static final Object lock = new Object();
    private static LayoutHierarchyPermissionComparator instance;

    /**
     * Returns the singleton instance of the comparator.
     * @return the singleton instance of the comparator.
     */
    public static LayoutHierarchyPermissionComparator getInstance() {
        synchronized (lock) {
            if (null == instance)
                instance = new LayoutHierarchyPermissionComparator();
        }
        return instance;
    }

    /**
     * Compares two layout hierarchy permissions so that those which don't allow relationships and those which
     * allow children are always first. Those which allow both parents and children come next. Those which
     * allow only parents come last.
     * @param o1 the first permission
     * @param o2 the second permission
     * @return -1, 0 or 1 depending on the outcome of the comparison between the first and second permissions
     */
    public int compare(LayoutHierarchyPermission o1, LayoutHierarchyPermission o2) {
        switch (o1) {
            case NONE:
                switch (o2) {
                    case NONE:
                    case ALLOW_CHILD:
                        return 0;
                    default:
                        return 1;
                }
            case ALLOW_CHILD:
                switch (o2) {
                    case NONE:
                    case ALLOW_CHILD:
                        return 0;
                    default:
                        return 1;
                }
            case ALLOW_ALL:
                switch (o2) {
                    case NONE:
                    case ALLOW_CHILD:
                        return -1;
                    case ALLOW_ALL:
                        return 0;
                    default:
                        return 1;
                }
            default:
                switch (o2) {
                    case ALLOW_PARENT:
                        return 0;
                    default:
                        return -1;
                }
        }
    }
}
