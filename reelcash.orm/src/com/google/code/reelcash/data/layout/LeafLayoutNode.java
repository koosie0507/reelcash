package com.google.code.reelcash.data.layout;

import com.google.code.reelcash.data.LayoutHierarchyPermission;
import java.util.List;

/**
 * Instances of this class represent leaf nodes in a layout tree. They only
 * accept parents and can never have any children.
 *
 * @author andrei.olar
 */
public class LeafLayoutNode extends DataLayoutNode {

    private DataLayoutNode master;

    public LeafLayoutNode(DataLayoutNode master, String name) {
        super(name, LayoutHierarchyPermission.ALLOW_PARENT);
        if (null == master)
            throw new IllegalArgumentException(Resources.getString("no_master_specified_error"));
        this.master = master;
        if (!this.master.getChildren().contains(this))
            this.master.getChildren().add(this);
    }

    @Override
    public List<DataLayoutNode> getChildren() {
        throw new UnsupportedOperationException(Resources.getString("no_children_on_leaf_error"));
    }

    @Override
    public Iterable<DataLayoutNode> getDescendants() {
        throw new UnsupportedOperationException(Resources.getString("no_children_on_leaf_error"));
    }

    @Override
    public DataLayoutNode getParent() {
        return master;
    }

    /**
     * 
     * @param master
     */
    @Override
    public void setParent(DataLayoutNode master) {
        if (null == master)
            throw new IllegalArgumentException(Resources.getString("null_master_error"));
        this.master = master;
    }
}
