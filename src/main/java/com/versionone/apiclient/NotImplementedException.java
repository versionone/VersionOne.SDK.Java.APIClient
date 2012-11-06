package com.versionone.apiclient;


/**
 * Used when something (feature, condition, etc) is not implemented in this version on the API
 * @author jerry
 *
 */
public class NotImplementedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create
	 */
	public NotImplementedException() {
		super("Method has no implementation");
	}
}
