package com.google.code.reelcash.model;

import com.google.code.reelcash.data.layout.DataLayout;
import com.google.code.reelcash.data.layout.DataLayoutNode;
import com.google.code.reelcash.data.layout.LeafLayoutNode;
import com.google.code.reelcash.data.layout.RootLayoutNode;
import java.util.ArrayList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author andrei.olar
 */
public class DataLayoutTreeModel implements TreeModel {

    private static final Object SYNC_ROOT = new Object();
    private final ArrayList<TreeModelListener> listeners;
    private final DataLayout modelObject;

    public DataLayoutTreeModel(DataLayout modelObject) {
        this.listeners = new ArrayList<TreeModelListener>();
        this.modelObject = modelObject;
    }

    private void fireTreeStructuredChanged(TreePath path) {
        TreeModelEvent e = new TreeModelEvent(this, path, null, null);
        for (TreeModelListener listener : listeners) {
            listener.treeStructureChanged(e);
        }
    }

    private void fireTreeItemAdded(DataLayoutNode node) {
        int[] childIndices;
        Object[] added = {node};
        childIndices = (node.allowsParent())
                ? new int[]{node.getParent().getChildren().indexOf(node)}
                : new int[]{modelObject.indexOf((RootLayoutNode) node)};

        TreeModelEvent e = new TreeModelEvent(this, getParentPath(node), childIndices, added);
        for (TreeModelListener listener : listeners) {
            listener.treeNodesInserted(e);
        }
    }

    private void fireTreeItemRemoved(DataLayoutNode node) {
        int[] childIndices;
        Object[] removed = {node};
        childIndices = (node.allowsParent())
                ? new int[]{node.getParent().getChildren().indexOf(node)}
                : new int[]{modelObject.indexOf((RootLayoutNode) node)};

        TreeModelEvent e = new TreeModelEvent(this, getParentPath(node), childIndices, removed);
        for (TreeModelListener listener : listeners) {
            listener.treeNodesRemoved(e);
        }
    }

    private void fireRootChanged() {
        TreeModelEvent e = new TreeModelEvent(this, new TreePath(modelObject), null, null);
        for (TreeModelListener listener : listeners) {
            listener.treeNodesChanged(e);
        }
    }

    private void fireTreeItemChanged(DataLayoutNode node) {
        Object[] nodes = getNodeWithAncestors(node);
        Object[] pathComponents = new Object[nodes.length + 1];
        pathComponents[0] = modelObject;
        for (int i = 1; i < pathComponents.length; i++) {
            pathComponents[i] = nodes[i - 1];
        }
        TreePath path = new TreePath(pathComponents);
        TreeModelEvent e = new TreeModelEvent(this, path, null, null);
        for (TreeModelListener listener : listeners) {
            listener.treeNodesChanged(e);
        }
    }

    public void addRoot(DataLayoutNode node) {
        synchronized (SYNC_ROOT) {
            modelObject.addRoot(node);
            fireTreeItemAdded(node);
        }
    }

    public void addTreeModelListener(TreeModelListener l) {
        synchronized (SYNC_ROOT) {
            listeners.add(l);
        }
    }

    private Object[] getNodeWithAncestors(DataLayoutNode node) {
        Object[] treePathComponents = new Object[node.level()];
        DataLayoutNode cursor = node;
        for (int i = treePathComponents.length - 1; i > -1; i--) {
            treePathComponents[i] = cursor;
            if (cursor.allowsParent())
                cursor = cursor.getParent();
        }
        return treePathComponents;
    }

    private TreePath getParentPath(DataLayoutNode node) {
        Object[] parentPathComponents = new Object[node.level() + 1];
        parentPathComponents[0] = modelObject;
        if (node.allowsParent()) {
            Object[] nodeAncestors = getNodeWithAncestors(node.getParent());
            for (int idx = 1; idx < parentPathComponents.length; idx++)
                parentPathComponents[idx] = nodeAncestors[idx - 1];
        }

        return new TreePath(parentPathComponents);
    }

    public Object getChild(Object parent, int index) {
        if (parent instanceof DataLayout)
            return modelObject.getRoot(index);
        if (parent instanceof DataLayoutNode) {
            DataLayoutNode parentNode = (DataLayoutNode) parent;
            return (parentNode.allowsChildren()) ? parentNode.getChildren().get(index) : null;
        }

        throw new UnsupportedOperationException(Resources.getString("invalid_tree_model_parent_type"));
    }

    public int getChildCount(Object parent) {
        if (parent instanceof DataLayout)
            return modelObject.size();
        if (parent instanceof DataLayoutNode) {
            DataLayoutNode parentNode = (DataLayoutNode) parent;
            return parentNode.allowsChildren() ? parentNode.getChildren().size() : 0;
        }

        return 0;
    }

    public int getIndexOfChild(Object parent, Object child) {
        if (child instanceof DataLayoutNode) {
            if (parent instanceof DataLayout)
                return modelObject.indexOf((DataLayoutNode) child);
            if (parent instanceof DataLayoutNode) {
                DataLayoutNode parentNode = (DataLayoutNode) parent;
                return parentNode.allowsChildren() ? parentNode.getChildren().indexOf(child) : -1;
            }
        }
        return -1;
    }

    public Object getRoot() {
        return modelObject;
    }

    public boolean isLeaf(Object node) {
        if (node instanceof DataLayoutNode) {
            DataLayoutNode dataNode = (DataLayoutNode) node;
            if (dataNode.allowsChildren())
                return dataNode.getChildren().isEmpty();
            return true;
        }
        return false;
    }

    public void removeTreeModelListener(TreeModelListener l) {
        synchronized (SYNC_ROOT) {
            listeners.remove(l);
        }
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        if (newValue instanceof DataLayoutNode) {
            Object changedNode = path.getLastPathComponent();
            if (changedNode instanceof RootLayoutNode)

                // must ensure the new value is also a root, can't just add anything to the layout
                if (newValue instanceof RootLayoutNode) {
                    modelObject.replace((RootLayoutNode) changedNode, (RootLayoutNode) newValue);
                    fireTreeStructuredChanged(path.getParentPath());
                    return;
                }

            if (changedNode instanceof DataLayoutNode) {
                DataLayoutNode parent = ((DataLayoutNode) changedNode).getParent();

                if (parent.getChildren().contains((DataLayoutNode) newValue))
                    return;
                int index = parent.getChildren().indexOf(changedNode);
                parent.getChildren().remove(index);
                parent.getChildren().add(index, (DataLayoutNode) newValue);
                fireTreeStructuredChanged(path.getParentPath());
                return;
            }
        }

        throw new UnsupportedOperationException(Resources.getString("unsupported_path_change"));
    }
}
