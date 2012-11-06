package com.versionone.apiclient;

/**
 * Exception thrown when the API encounters a problem related to Object Identifiers 
 * @author jerry
 *
 */
public class OidException extends V1Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create with message and token 
	 * @param message - description of error 
	 * @param token - token of object that caused problem
	 */
	public OidException(String message, String token) {
		this(message, token, null);
	}

	/**
	 * Create with message, token and another exception
	 * @param message - description of error
	 * @param token - token of object that caused problem
	 * @param inner - exception that we caught
	 */
	public OidException(String message, String token, Exception inner) {
		this(message + ": " + token, inner);
	}

	/**
	 * Create with just a message
	 * @param message - description of error
	 */
	public OidException(String message) {
		super(message, null);
	}

	/**
	 * Create with a message and another exception
	 * @param message - description of error
	 * @param inner - exception that we caught
	 */
	public OidException(String message, Exception inner) {
		super(message, inner);
	}
}