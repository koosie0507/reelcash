/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout;

import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.LayoutHierarchyPermission;
import com.google.code.reelcash.data.LayoutHierarchyPermissionComparator;
import com.google.code.reelcash.data.layout.fields.*;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author andrei.olar
 */
public abstract class DataLayoutNode implements Comparable<DataLayoutNode>, Iterable<Field> {

    private static final Object syncRoot = new Object();
    private final String name;
    private final LayoutHierarchyPermission layoutPermission;
    private String text;
    private FieldList fieldList;

    /**
     * Initializes a new layout node.
     * @param name name of the node.
     * @param layoutPermission specifies what permissions are granted to the node for relating to
     * other data layout nodes.
     */
    protected DataLayoutNode(String name, LayoutHierarchyPermission layoutPermission) {
        if (null == name)
            throw new IllegalArgumentException(Resources.getString("null_layout_node_name_error"));
        this.name = name;
        text = this.name;
        this.layoutPermission = layoutPermission;
    }

    /**
     * Tests whether the node allows having a parent.
     * @return whether the node allows having a parent.
     */
    public boolean allowsParent() {
        switch (layoutPermission) {
            case ALLOW_ALL:
            case ALLOW_PARENT:
                return true;
            default:
                return false;
        }
    }

    /**
     * Tests whether the node allows having child nodes.
     * @return true whether the node allows having child nodes.
     */
    public boolean allowsChildren() {
        switch (layoutPermission) {
            case ALLOW_ALL:
            case ALLOW_CHILD:
                return true;
            default:
                return false;
        }
    }

    /**
     * Compares the current data layout node to the other data layout node.
     * @param o other data layout node.
     * @return -1, 0 or 1 depending on how the current layout node compares to the other layout node.
     */
    public int compareTo(DataLayoutNode o) {
        if (this == o)
            return 0;
        if (null == o)
            return 1;

        int result = LayoutHierarchyPermissionComparator.getInstance().compare(layoutPermission, o.layoutPermission);
        return (0 == result) ? name.compareToIgnoreCase(o.name) : result;
    }

    /**
     * Returns a new data row which uses the fields specified by the current instance' field list.
     *
     * @return new data row.
     */
    public DataRow createRow() {
        return new DataRow(getFieldList());
    }

    /**
     * Computes the current layout node's level.
     * @return
     */
    public int level() {
        try {
            int level = 1;
            DataLayoutNode parent = getParent();
            while (null != parent) {
                level++;
                parent = parent.getParent();
            }
            return level;
        }
        catch (UnsupportedOperationException e) {
            // if we can't get the parent, we're at level 1 - root or flat
            return 1;
        }
    }

    /**
     * Returns true if the other object is a data layout node with the same name as the current node.
     * 
     * @param obj another data layout node.
     * @return true, if the current node equals the given node.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final DataLayoutNode other = (DataLayoutNode) obj;
        if (name == null ? other.name == null : name.equals(other.name))
            return true;
        return false;
    }

    /**
     * Returns the children of the current node.
     * @return the child nodes (no other descendants) of the current data layout node.
     */
    public abstract List<DataLayoutNode> getChildren();

    /**
     * Returns all of the descendants of the current layout node enumerated breadth first.
     * @return all of the descendants of the current layout node enumerated breadth first.
     */
    public abstract Iterable<DataLayoutNode> getDescendants();

    /**
     * Gets the field set which is used for holding information on the fields of the current layout node.
     * @return field set.
     */
    public FieldList getFieldList() {
        synchronized (syncRoot) {
            if (null == fieldList)
                fieldList = new FieldList();
        }
        return fieldList;
    }

    /**
     * Gets what the current node is allowed to have: parent, children, none or both.
     * @return layout arrangement with other nodes permissions.
     */
    public LayoutHierarchyPermission getLayoutPermission() {
        return layoutPermission;
    }

    /**
     * Gets the name which is associated to the current node. In a collection, a node may only have
     * one name.
     * @return the name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the parent node of the current node.
     *
     * @return parent of the current layout node.
     */
    public abstract DataLayoutNode getParent();

    /**
     * Returns a description (caption) for the current node.
     * @return a caption text for the current layout node.
     */
    public String getText() {
        if (null == text)
            text = name;
        return text;
    }

    /**
     * Tests whether the current data layout node can only be a root node. This is true
     * if the node doesn't allow a master.
     * @return true, if the node doesn't allow master nodes.
     */
    public boolean isRoot() {
        return layoutPermission == LayoutHierarchyPermission.ALLOW_CHILD;
    }

    /**
     * Tests whether the current data layout node is a leaf node. This is true if the
     * current layout node will not allow child nodes. If the node doesn't allow children,
     * nor parents this method will still return false because the node can't be part of
     * a tree, making the leaf notion nonsensical.
     * 
     * @return true, if the current layout node will not allow parent nodes.
     */
    public boolean isLeaf() {
        return layoutPermission == LayoutHierarchyPermission.ALLOW_PARENT;
    }

    /**
     * Tests whether the current data layout node will allow parents or children. If it DOESN'T
     * then it's a flat node.
     * 
     * @return true, if the current layout node will not allow parents, nor children.
     */
    public boolean isFlat() {
        return layoutPermission == LayoutHierarchyPermission.NONE;
    }

    /**
     * Tests whether the current node will allow both parents and children.
     *
     * @return true, if the current node allows both parents and children.
     */
    public boolean isLink() {
        return layoutPermission == LayoutHierarchyPermission.ALLOW_ALL;
    }

    /**
     * Returns an iterator over the fields of the current layout node.
     * 
     * @return an iterator over the fields of the current layout node.
     */
    public Iterator<Field> iterator() {
        return fieldList.iterator();
    }

    /**
     * Hash code based on the name of the layout node.
     * @return hash code based on name.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /**
     * Sets the parent of the current node.
     *
     * @param value parent of the current layout node.
     */
    public abstract void setParent(DataLayoutNode value);

    /**
     * Sets the description text to the given value.
     * 
     * @param value new description text.
     */
    public void setText(String value) {
        text = value;
    }

    /**
     * Provides a readable description of the data layout node.
     * @return the name of the node.
     */
    @Override
    public String toString() {
        return getText();
    }
}
