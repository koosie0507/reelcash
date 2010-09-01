/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.code.reelcash.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Instances of this class close windows.
 * @author cusi
 */
public class CloseAction extends AbstractAction {

    private Window closed;

    /**
     * Creates a new close window action.
     * @param closed the window to close.
     */
    public CloseAction(Window closed) {
        this.closed = closed;
    }

    /**
     * Closes a window.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        closed.setVisible(false);
    }
}
