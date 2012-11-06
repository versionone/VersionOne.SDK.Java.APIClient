package com.versionone.apiclient;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for process and store cookies for requests to the VersionOne service
 *
 * @author VersionOne
 *
 */
public class CookiesManager extends HashMap<String, CookieData> implements
		ICookiesManager {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5115415390702250886L;
	private static final String SET_COOKIE_PARAM = "Set-Cookie";
	private static final String COOKIE_PARAM = "Cookie";
	private static final String COOKIES_DATA_DELIMITER = ";";
	private static final String COOKIES_SEPARATOR = "; ";
	private static final String NAME_VALUE_SEPARATOR = "=";
	private static final String EXPIRES = "expires";
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"EEE, dd-MMM-yyyy hh:mm:ss z", Locale.US);

	private static Map<CookieKey, CookiesManager> cookiesManagers = new HashMap<CookieKey, CookiesManager>();

	private CookiesManager() {
		super();
	}

	private CookiesManager(int size) {
		super(size);
	}

	public static CookiesManager getCookiesManager(String path) {
		return getCookiesManager(path, null, null);
	}

	public static CookiesManager getCookiesManager(String path, String userName, String password) {
        CookieKey key = new CookieKey(getDomenToken(path), userName, password);

		if (!cookiesManagers.containsKey(key)) {
            cookiesManagers.put(key, new CookiesManager());
        }
		return cookiesManagers.get(key);
	}

	/**
	 * Generate unique token based on path and user name
	 *
	 * @param url - path to VersionOne
	 * @return - unique token
	 */
	public static String getDomenToken(String url) {
		String token = url;
		try {
			URL urlData = new URL(url);
			token = urlData.getHost();
		} catch (MalformedURLException e) {
		}
		if (token.startsWith("www.")) {
			token = token.replaceFirst("www.", "");
		}
		// token += "%%" + userName;

		return token;
	}

	private void addCookie(String cookie) {
		if (cookie != null && !cookie.equals("")) {
			StringTokenizer cookieData = new StringTokenizer(cookie,
					COOKIES_DATA_DELIMITER);
			if (cookieData.hasMoreTokens()) {
				String data = cookieData.nextToken();
				String name = data.substring(0, data
						.indexOf(NAME_VALUE_SEPARATOR));
				String value = data.substring(data
						.indexOf(NAME_VALUE_SEPARATOR) + 1, data.length());

				Date expires = null;
				while (cookieData.hasMoreTokens()) {
					data = cookieData.nextToken();
					String[] attibutesData = data.split(NAME_VALUE_SEPARATOR);
					if (attibutesData[0].trim().equals(EXPIRES)) {
						try {
							expires = dateFormat.parse(attibutesData[1].trim());
						} catch (ParseException ex) {
							System.out.println("incorrect data:"
									+ ex.getMessage());
						}
					}
				}
				addCookie(name, value, expires);
			}
		}
	}

	/**
	 * Creates string with cookies for request. Removes all expired cookies
	 *
	 * @return cookies for addition to request
	 */
	public String getCookies() {
		String cookies = "";
		if (size() > 0) {
			boolean isFirst = true;
			List<String> cookiesForDelite = new ArrayList<String>();
			for (String name : keySet()) {
				CookieData cookiesData = get(name);
				if (isNotExpired(cookiesData.getExpire())) {
					if (!isFirst) {
						cookies += COOKIES_SEPARATOR;
					} else {
						isFirst = false;
					}
					cookies += cookiesData.getName() + NAME_VALUE_SEPARATOR
							+ cookiesData.getValue();
				} else {
					cookiesForDelite.add(name);
				}
			}
			deleteCookies(cookiesForDelite);
		}

		return cookies != "" ? cookies : null;
	}

	private void deleteCookies(List<String> cookiesForDelite) {
		for (String name : cookiesForDelite) {
			deleteCookie(name);
		}
	}

	/**
	 * Removes cookie from the storage
	 */
	public void deleteCookie(String name) {
		remove(name);
	}

	/**
	 * Store cookie for using in next requests
	 *
	 * @param headerData
	 *            Map of response header data
	 */
	public void addCookie(Map<String, List<String>> headerData) {
		List<String> listCookies = headerData.get(SET_COOKIE_PARAM);
		if (listCookies != null && listCookies.size() > 0) {
			for (String cookie : listCookies) {
				addCookie(cookie);
			}
		}
	}

	/**
	 * Add cookie to storage
	 *
	 * @param name -
	 *            cookie name
	 * @param value -
	 *            cookie value
	 * @param expires -
	 *            date when cookie will expire
	 */
	public void addCookie(String name, String value, Date expires) {
		put(name, new CookieData(name, value, expires));
	}

	private boolean isNotExpired(Date expiresDate) {
		if (expiresDate == null)
			return true;
		Date now = new Date();
		return (now.compareTo(expiresDate) <= 0);
	}

	/**
	 * Add cookies to requests to the VersionOne service
	 *
	 * @param request
	 *            HttpURLConnection to VersionOne server
	 */
	protected void addCookiesToRequest(HttpURLConnection request) {
		if (request == null) {
			return;
		}
		String cookies = getCookies();
		if (cookies != null) {
			request.setRequestProperty(COOKIE_PARAM, cookies);
		}
	}

	public String getCookie(String name) {
		if (containsKey(name)) {
			return get(name).getValue();
		}
		return null;
	}

	public void deleteAllCookies() {
		clear();
	}

}

// - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -
//                                          I N N E R    C L A S S E S
// - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -  - - -

class CookieData {
	private Date expire;
	private String name;
	private String value;

	public CookieData(String name, String value, Date expire) {
		this.expire = expire;
        this.name = name;
        this.value = value;
    }

    public Date getExpire() {
        return expire;
    }

    public String getName() {
        return name;
    }

	public String getValue() {
		return value;
	}
}

class CookieKey {
    private String domenToken;
    private String userName;
    private String password;

    CookieKey(String domenToken, String userName, String password) {
        this.domenToken = domenToken;
        this.userName = userName;
        this.password = password;
    }

    public String getDomenToken() {
        return domenToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CookieKey)) {
            return false;
        }
        CookieKey other = (CookieKey) obj;
        return domenToken != null ? password != null ? userName != null
                ? domenToken.equals(other.getDomenToken())
                        && password.equals(other.getPassword()) && userName.equals(other.getUserName())
                : domenToken.equals(other.getDomenToken())
                        && password.equals(other.getPassword()) && other.getUserName() == null
                : domenToken.equals(other.getDomenToken())
                        && other.getPassword() == null && other.getUserName() == null
                : other.getDomenToken() == null;
    }

    public int hashCode() {
        return  domenToken != null ? password != null ? userName != null
                ? 29 + 7 * domenToken.hashCode() + 3 * password.hashCode() + 5 * userName.hashCode()
                : 29 + 7 * domenToken.hashCode() + 3 * password.hashCode()
                : 29 + 7 * domenToken.hashCode()
                : 29;
    }
}
