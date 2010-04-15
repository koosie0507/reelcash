/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.swing;

import com.google.code.reelcash.GlobalResources;
import com.google.code.reelcash.data.DataRow;
import com.google.code.reelcash.data.geo.CountyNode;
import com.google.code.reelcash.data.sql.QueryMediator;
import com.google.code.reelcash.model.DataRowComboModel;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * Represents an ancestor listener suitable for all and any field displays.
 * 
 * @author andrei.olar
 */
public class RefreshComponentDataListener implements AncestorListener {
    private final DataSource dataSource;
    private final DataRowComboModel comboModel;
    private final ReferenceFieldCellRenderer tableCellRenderer;
    private QueryMediator queryMediator;

    /**
     *
     * @param dataSource
     * @param comboModel
     * @param tableCellRenderer
     */
    public RefreshComponentDataListener(DataSource dataSource, DataRowComboModel comboModel, ReferenceFieldCellRenderer tableCellRenderer) {
        this.dataSource = dataSource;
        this.comboModel = comboModel;
        this.tableCellRenderer = tableCellRenderer;
    }

    private QueryMediator getQueryMediator() {
        if (null == queryMediator)
            queryMediator = new QueryMediator(dataSource);
        return queryMediator;
    }

    public void ancestorAdded(AncestorEvent event) {
        // the component is made visible
        if (null != comboModel || null != tableCellRenderer)
            try {
                List<DataRow> rows = getQueryMediator().fetchAll(CountyNode.getInstance());
                if (null != comboModel)
                    comboModel.fill(rows);
                if (null != tableCellRenderer)
                    tableCellRenderer.setData(rows);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        GlobalResources.getString("application_error_title"),
                        JOptionPane.ERROR_MESSAGE);
            }
    }

    public void ancestorRemoved(AncestorEvent event) {
        if (null != comboModel)
            comboModel.clear();
    }

    public void ancestorMoved(AncestorEvent event) {
        // not implemented
    }
}
