package com.versionone.apiclient;

/**
 * Represents a generic API exception
 * @author jerry
 *
 */
public class APIException extends V1Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create with message and token
	 * @param message - error message
	 * @param token - VersionOne object token 
	 */
	public APIException(String message, String token) {
		this(message, token, null);
	}

	/**
	 * Create with meassage, token, and exception 
	 * @param message
	 * @param token
	 * @param inner
	 */
	public APIException(String message, String token, Exception inner) {
		this(message + ": " + token, inner);
	}

	/**
	 * Create with just a message
	 * @param message
	 */
	public APIException(String message) {
		super(message, null);
	}

	/**
	 * Create with message and exception
	 * @param message
	 * @param inner
	 */
	public APIException(String message, Exception inner) {
		super(message, inner);
	}
}