/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The data layout class provides an arborescent representation of the
 * data structures involved in a particular business context. Thus, a data
 * layout might be created for a particular frame in which various independent
 * data nodes will take part.
 *
 * @author cusi
 */
public abstract class DataLayout {

    private static final Object SYNC_ROOT = new Object();
    private final ArrayList<DataLayoutNode> roots;

    protected DataLayout() {
        roots = new ArrayList<DataLayoutNode>(1);
    }

    protected DataLayout(DataLayoutNode root) {
        roots = new ArrayList<DataLayoutNode>(1);
        roots.add(root);
    }

    protected DataLayout(DataLayoutNode... roots) {
        this.roots = new ArrayList<DataLayoutNode>(roots.length);
        for (DataLayoutNode root : roots)
            this.roots.add(root);
    }

    /**
     * Retrieves the root layout node of the current data layout located at
     * the given index.
     *
     * @param index 0 based index.
     * @return root layout nodes of current data layout.
     */
    public DataLayoutNode getRoot(int index) {
        return roots.get(index);
    }

    public void addRoot(DataLayoutNode node) {
        synchronized (SYNC_ROOT) {
            roots.add(node);
        }
    }

    public void deleteRoot(DataLayoutNode node) {
        synchronized (SYNC_ROOT) {
            roots.remove(node);
        }
    }

    public void deleteRootAt(int index) {
        synchronized (SYNC_ROOT) {
            roots.remove(index);
        }
    }

    public int indexOf(DataLayoutNode node) {
        return roots.indexOf(node);
    }

    public Iterator<DataLayoutNode> iterator() {
        ArrayDeque<DataLayoutNode> queue = new ArrayDeque<DataLayoutNode>();
        ArrayList<DataLayoutNode> iterated = new ArrayList<DataLayoutNode>();
        for (DataLayoutNode root : roots) {
            queue.push(root);
        }
        while (!queue.isEmpty()) {
            DataLayoutNode node = queue.pop();
            for (DataLayoutNode child : node.getChildren()) {
                queue.push(child);
            }
            iterated.add(node);
        }
        return iterated.iterator();
    }

    public void replace(DataLayoutNode oldValue, DataLayoutNode newValue) {
        if (!roots.contains(oldValue) || roots.contains(newValue))
            return;

        int index = roots.indexOf(oldValue);
        roots.remove(index);
        roots.add(index, newValue);
    }

    public int size() {
        return roots.size();
    }
}

