package com.google.code.reelcash;

/**
 * This exception is raised as a runtime error by the application.
 *
 * @author andrei.olar
 */
public class ReelcashException extends RuntimeException {

	private static final long serialVersionUID = -2681040028824289837L;

	/**
	 * Creates a new application error.
	 */
	public ReelcashException() {
		super("Application error");
	}

	/**
	 * Creates a new application error.
	 * @param message error message
	 */
	public ReelcashException(String message) {
		super(message);
	}

	/**
	 * Creates a new application error.
	 * @param message error message
	 * @param t error cause
	 */
	public ReelcashException(String message, Throwable t) {
		super(message, t);
	}

	/**
	 * Creates a new application error.
	 * @param t error cause.
	 */
	public ReelcashException(Throwable t) {
		super(t);
	}
}
