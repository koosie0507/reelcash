package com.google.code.reelcash.data;

/**
 * Enumerates the allowed permissions for a data layout node. A layout node may
 * allow having a parent node, it may allow having child nodes, it may allow
 * both situations or it may not allow any of the two situations.
 * 
 * @author andrei.olar
 */
public enum LayoutHierarchyPermission {

    /**
     * No relationships between the data layout node and other nodes are allowed.
     */
    NONE,
    /**
     * The data layout node allows having a parent layout node.
     */
    ALLOW_PARENT,
    /**
     * The data layout node allows having one or more child layout nodes.
     */
    ALLOW_CHILD,
    /**
     * The data layout node allows having a parent node and one or more child layout nodes.
     */
    ALLOW_ALL
}
