package com.versionone.apiclient;

/**
 * Represents a generic API exception
 */
public class APIException extends V1Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Create with message and token
	 * 
	 * @param message - error message
	 * @param token - VersionOne object token 
	 */
	public APIException(String message, String token) {
		this(message, token, null);
	}

	/**
	 * Create with message, token, and exception 
	 * 
	 * @param message - String data
	 * @param token - String data
	 * @param inner - Exception
	 */
	public APIException(String message, String token, Exception inner) {
		this(message + ": " + token, inner);
	}

	/**
	 * Create with just a message
	 * 
	 * @param message - String data
	 */
	public APIException(String message) {
		super(message, null);
	}

	/**
	 * Create with message and exception
	 * 
	 * @param message - String data
	 * @param inner - Exception
	 */
	public APIException(String message, Exception inner) {
		super(message, inner);
	}
}