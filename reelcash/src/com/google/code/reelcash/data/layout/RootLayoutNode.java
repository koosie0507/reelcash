/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout;

import com.google.code.reelcash.data.LayoutHierarchyPermission;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class represent root nodes in a layout tree.
 * 
 * @author andrei.olar
 */
public class RootLayoutNode extends DataLayoutNode {

    private static final Object syncRoot = new Object();
    private ArrayList<? extends DataLayoutNode> children;

    /**
     * Creates a new root layout node. The node may only have children, no parents allowed.
     * @param name the name of the new root.
     */
    public RootLayoutNode(String name) {
        super(name, LayoutHierarchyPermission.ALLOW_CHILD);
    }

    /**
     * Returns the level 2 nodes in the tree.
     * 
     * @return the children of the root node.
     */
    @Override
    public Collection<? extends DataLayoutNode> getChildren() {
        synchronized (syncRoot) {
            if (null == children)
                children = new ArrayList();
        }
        return children;
    }

    /**
     * <p>Returns an iterable which provides all the descendants of the current root
     * in breadth-first order.</p>
     * <p>The method is thread safe</p>
     *
     * @return an iterable which provides the descendants of the current root in
     * breadth-first order.
     *
     */
    @Override
    public Iterable<? extends DataLayoutNode> getDescendants() {
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
     * Will throw an exception because root nodes don't have parents.
     * @return nothing
     * @throws UnsupportedOperationException
     */
    @Override
    public DataLayoutNode getParent() {
        throw new UnsupportedOperationException(Resources.getString("no_parent_on_root_error"));
    }

    /**
     * Will throw an exception because root nodes don't have parents.
     * @return nothing
     * @throws UnsupportedOperationException
     */
    @Override
    public void setParent(DataLayoutNode value) {
        throw new UnsupportedOperationException(Resources.getString("no_parent_on_root_error"));
    }
}
