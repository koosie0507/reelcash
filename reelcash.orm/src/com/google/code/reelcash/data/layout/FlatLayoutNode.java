package com.google.code.reelcash.data.layout;

import com.google.code.reelcash.data.LayoutHierarchyPermission;
import java.util.List;

/**
 * Represents a node which doesn't allow parent or children.
 *
 * @author andrei.olar
 */
public class FlatLayoutNode extends DataLayoutNode {

    /**
     * Creates a new flat layout node.
     *
     * @param name node name.
     */
    public FlatLayoutNode(String name) {
        super(name, LayoutHierarchyPermission.NONE);
    }

    @Override
    public List<DataLayoutNode> getChildren() {
        throw new UnsupportedOperationException(Resources.getString("no_children_on_flat_error"));
    }

    @Override
    public Iterable<DataLayoutNode> getDescendants() {
        throw new UnsupportedOperationException(Resources.getString("no_children_on_flat_error"));
    }

    @Override
    public DataLayoutNode getParent() {
        throw new UnsupportedOperationException(Resources.getString("no_parent_on_flat_error"));
    }

    @Override
    public void setParent(DataLayoutNode value) {
        throw new UnsupportedOperationException(Resources.getString("no_parent_on_flat_error"));
    }
}
