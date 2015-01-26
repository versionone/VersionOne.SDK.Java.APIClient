package com.versionone.apiclient;

/**
 * Base class for all VersionOne Java SDK exceptions
 */
public class V1Exception extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create with just a message.  Used when the exception is internal.
	 * 
	 * @param message - String
	 */
	public V1Exception(String message) {
		super(message);
	}

	/**
	 * Create with a message and nested exception.  Used when an exception is being re-thrown
	 * 
	 * @param message - String
	 * @param innerException - Exception
	 */
	public V1Exception(String message, Exception innerException) {
		super(message, innerException);
	}
}