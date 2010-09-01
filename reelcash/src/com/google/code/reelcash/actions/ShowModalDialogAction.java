/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.actions;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 *
 * @author andrei.olar
 */
public class ShowModalDialogAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private JDialog dialog;

    /**
     * Creates a new action which will show the given modal dialog.
     * @param dialog dialog to show.
     */
    public ShowModalDialogAction(JDialog dialog) {
        this.dialog = dialog;
    }

    public void actionPerformed(ActionEvent e) {
        dialog.setModal(true);
        int x = (int) (dialog.getToolkit().getScreenSize().getWidth()
                - dialog.getPreferredSize().getWidth()) / 2;
        int y = (int) (dialog.getToolkit().getScreenSize().getHeight()
                - dialog.getPreferredSize().getHeight()) / 2;
        dialog.setLocation(new Point(x,y));
        dialog.pack();
        dialog.setVisible(true);
    }
}
