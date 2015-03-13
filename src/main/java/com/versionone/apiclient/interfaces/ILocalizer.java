package com.versionone.apiclient.interfaces;

import com.versionone.apiclient.exceptions.V1Exception;


/**
 * Interface required for localizers
 */
public interface ILocalizer {

	/**
	 * Resolve the key to it's localized value
	 * 
	 * @param key - String
	 * @return Localized String value
	 * @throws V1ConnectionException 
	 */
	String resolve(String key) throws V1Exception;

}
