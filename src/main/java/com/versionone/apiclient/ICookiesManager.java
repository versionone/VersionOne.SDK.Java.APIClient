package com.versionone.apiclient;

import java.util.Date;

public interface ICookiesManager {
    /**
     * Adds cookie to storage.
     *
     * @param name - cookie name.
     * @param value - cookie value.
     * @param expires - date when cookie will expire.
     */
    void addCookie(String name, String value, Date expires);

    /**
     * Removes cookie from the storage.
     *
     * @param name - cookie name.
     */
    void deleteCookie(String name);

    /**
     * Gets cookie from the storage.
     *
     * @param name - cookie name.
     * @return cookie value
     */
    String getCookie(String name);

    /**
     * Deletes all cookies from the storage
     */
	void deleteAllCookies();
}
