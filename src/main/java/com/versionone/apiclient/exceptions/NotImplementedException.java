package com.versionone.apiclient.exceptions;


/**
 * Used when something (feature, condition, etc) is not implemented in this version on the API
 */
public class NotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotImplementedException() {
		super("Method has no implementation");
	}
}
