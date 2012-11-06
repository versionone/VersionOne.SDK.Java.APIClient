package com.versionone.apiclient;

/**
 * Interface required for localizers
 * @author jerry
 *
 */
public interface ILocalizer {

	/**
	 * Resolve the key to it's localized value
	 * @param key
	 * @return Localized String value
	 */
	String resolve(String key);

}
