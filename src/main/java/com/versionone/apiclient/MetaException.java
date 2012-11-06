package com.versionone.apiclient;

/**
 * Indicate an unrecoverable error with MetaData
 * @author jerry
 *
 */
public class MetaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MetaException(String message, String token) {
		this(message + ": " + token);
	}

	public MetaException(String message, String token, Exception inner) {
		this(message + ": " + token, inner);
	}

	public MetaException(String message) {
		super(message);
	}

	public MetaException(String message, Exception inner) {
		super(message, inner);
	}
}