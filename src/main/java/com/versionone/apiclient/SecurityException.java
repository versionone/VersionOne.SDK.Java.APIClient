package com.versionone.apiclient;

/**
 * Exception thrown when the APIClient encounters a security exception (401)
 * @author jerry
 *
 */
public class SecurityException extends ConnectionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct
	 */
	public SecurityException() {
		super("User Not Authorized", 401);
	}

}
