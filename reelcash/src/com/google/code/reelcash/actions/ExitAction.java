/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.code.reelcash.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Exits the application.
 * @author andrei.olar
 */
public class ExitAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private int exitCode;

	/**
	 * Creates an exit action with the specified exit code.
	 * @param exitCode 
	 */
	public ExitAction(int exitCode) {
		this.exitCode = exitCode;
	}

	public void actionPerformed(ActionEvent e) {
		System.exit(exitCode);
	}
}
