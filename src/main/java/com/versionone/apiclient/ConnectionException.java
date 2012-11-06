package com.versionone.apiclient;

/**
 * Exception thrown for connection errors
 * @author jerry
 *
 */
public class ConnectionException extends V1Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int _responseCode;

	/**
	 * create with message
	 * @param message - text message for user
	 * @param responseCode - server response code
	 */
	public ConnectionException(String message, int responseCode) {
		super(message);
		_responseCode = responseCode;
	}

	/**
	 * Create with message and inner exception and response code
	 * @param message - text message for user
	 * @param responseCode - server response code
	 * @param inner - nested exception
	 */
	public ConnectionException(String message, int responseCode, Exception inner) {
		super(message, inner);
		_responseCode = responseCode;
	}

	/**
	 * Create with message and inner exception
	 * This case sets response code to -1
	 * @param message text of error
	 * @param inner inner exception
	 */
	public ConnectionException(String message, Exception inner) {
		this(message, -1, inner);
	}

	/**
	 * Create with message
	 *
	 * @param message text of error
	 */
	public ConnectionException(String message) {
		this(message, null);
	}

	/**
	 * Return the response code the server returned
	 * @return response code
	 */
	public int getServerResponseCode() {
		return _responseCode;
	}
}