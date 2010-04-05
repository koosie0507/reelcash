/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.data.layout;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
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

    private RootLayoutNode[] roots;

    protected DataLayout() {
        roots = new RootLayoutNode[1];
    }

    protected DataLayout(RootLayoutNode root) {
        this.roots = new RootLayoutNode[]{root};
    }

    protected DataLayout(RootLayoutNode[] roots) {
        this.roots = roots;
    }

    /**
     * Retrieves the root layout node of the current data layout located at
     * the given index.
     *
     * @param index 0 based index.
     * @return root layout nodes of current data layout.
     */
    public RootLayoutNode getRoot(int index) {
        return roots[index];
    }

    public void addRoot(RootLayoutNode node) {

    }

    public void deleteRoot(RootLayoutNode node) {
    }

    public void deleteRootAt(int index) {
    }

    public Iterator<DataLayoutNode> iterator() {
        ArrayDeque<DataLayoutNode> queue = new ArrayDeque<DataLayoutNode>();
        ArrayList<DataLayoutNode> iterated = new ArrayList<DataLayoutNode>();
        for(RootLayoutNode root: roots) {
            queue.push(root);
        }
        while(!queue.isEmpty()) {
            DataLayoutNode node = queue.pop();
            for(DataLayoutNode child: node.getChildren()) {
                queue.push(child);
            }
            iterated.add(node);
        }
        return iterated.iterator();
    }
}

