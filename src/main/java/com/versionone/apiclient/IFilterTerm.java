package com.versionone.apiclient;

/**
 * Interface required for Filter Terms
 * @author jerry
 *
 */
public interface IFilterTerm {
	/**
	 * @return Full token
	 * @throws APIException if object has incorrect statement
	 */
	public String getToken() throws APIException;

    /**
     * @return Short token
     * @throws APIException if object has incorrect statement
     */
    public String getShortToken() throws APIException;
}
