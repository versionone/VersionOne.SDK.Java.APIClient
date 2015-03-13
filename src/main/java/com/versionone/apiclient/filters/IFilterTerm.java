package com.versionone.apiclient.filters;

import com.versionone.apiclient.exceptions.APIException;

/**
 * Interface required for Filter Terms
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
