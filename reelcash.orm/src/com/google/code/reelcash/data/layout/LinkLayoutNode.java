/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout;

import com.google.code.reelcash.data.LayoutHierarchyPermission;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node which may have both a parent and children.
 * 
 * @author andrei.olar
 */
public class LinkLayoutNode extends DataLayoutNode {

    private static final Object syncRoot = new Object();
    private ArrayList<DataLayoutNode> children;
    private DataLayoutNode parent;

    /**
     * Creates a new link node in a layout.
     *
     * @param parent the parent node of the current link node. May not be null.
     * @param name the name of the current link node. May not be null.
     * 
     * @throws IllegalArgumentException if the name parameter or the parent parameter is null.
     */
    public LinkLayoutNode(DataLayoutNode parent, String name) {
        super(name, LayoutHierarchyPermission.ALLOW_ALL);
        if (null == parent)
            throw new IllegalArgumentException(Resources.getString("no_master_specified_error"));

        this.parent = parent;
        if (!this.parent.getChildren().contains(this))
            this.parent.getChildren().add(this);
    }

    /**
     * Returns the child nodes of the current node.
     *
     * @return the child nodes of the current node.
     */
    @Override
    public List<DataLayoutNode> getChildren() {
        synchronized (syncRoot) {
            if (null == children)
                children = new ArrayList();
        }
        return children;
    }

    /**
     * Returns the descendants of the current node. The node is not included in the iterable
     * set.
     *
     * @return an iterable set over the descendants of the current node enumerated breadth-first.
     */
    @Override
    public Iterable<DataLayoutNode> getDescendants() {
        ArrayList<DataLayoutNode> descendants = new ArrayList();
        synchronized (syncRoot) {
            // initialize queue with children
            ArrayDeque<DataLayoutNode> queue = new ArrayDeque(5);
            for (DataLayoutNode child : getChildren()) {
                queue.push(child);
            }

            // walk through the descendants of each child
            while (!queue.isEmpty()) {
                DataLayoutNode node = queue.pop();
                for (DataLayoutNode child : node.getChildren()) {
                    queue.push(child);
                }
                descendants.add(node);
            }
        }
        return descendants;
    }

    /**
     * Returns the parent node of the current link node.
     *
     * @return the parent node of the current link node.
     */
    @Override
    public DataLayoutNode getParent() {
        synchronized (syncRoot) {
            return parent;
        }
    }

    /**
     * Sets the parent node of the current link node.
     * @param value the new parent node.
     */
    @Override
    public void setParent(DataLayoutNode value) {
        if (null == value)
            throw new IllegalArgumentException(Resources.getString("null_master_error"));
        synchronized (syncRoot) {
            parent = value;
        }
    }
}
