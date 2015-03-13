package com.versionone.apiclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.SecurityException;
import com.versionone.apiclient.interfaces.IAPIConnector;
import com.versionone.apiclient.interfaces.ICookiesManager;
import com.versionone.utils.V1Util;

import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

/**
 * Note: This class has been deprecated from the VersionOne Java SDK. Use the {@link com.versionone.apiclient.V1Connector} class to make a connection to VersionOne. 
 */
@Deprecated
@SuppressWarnings("restriction")
public class V1APIConnector implements IAPIConnector {

	private static final String UTF8 = "UTF-8";

	private final CookiesManager cookiesManager;
	private String _url = null;
	private String _accessToken = null;
	private ProxyProvider proxy = null;
	private final Map<String, HttpURLConnection> _requests = new HashMap<String, HttpURLConnection>();
	public final Map<String, String> customHttpHeaders = new HashMap<String, String>();
	private String _user_agent_header = "";
	private static String _app_name;
	private static String _app_version;

	/**
	 * Create a connection with only the URL to the VersionOne server.
	 * 
	 * Use this constructor to access the VersionOne Meta API, which does not require the use of credentials.
	 *
	 * @param url URL of the VersionOne server
	 */
	public V1APIConnector(String url) {
		this(url, null, null, null);
	}
	
	/**
	 * Create a connection with only the URL to the VersionOne server through a proxy.
	 * 
	 * Use this constructor to access the VersionOne Meta API, which does not require the use of credentials.
	 *
	 * @param url URL to the VersionOne server
	 * @param proxy Proxy for the connection
	 */
	public V1APIConnector(String url, ProxyProvider proxy) {
		this(url, null, null, proxy);
	}
	
	/**
	 * Create a connection using a username and password. 
	 * 
	 * When using for Windows Integrated Authentication, pass null for the username and password. The SDK will use the currently logged in user's credentials.
	 * 
	 * @param url URL of the VersionOne server
	 * @param userName VersionOne username
	 * @param password VersionOne password
	 */
	public V1APIConnector(String url, String userName, String password) {
		this(url, userName, password, null);
	}

	/**
	 * Create a connection using username and password through a proxy.
	 * 
	 * When using for Windows Integrated Authentication, pass null for the password. The SDK will use the currently logged in user's credentials.
	 * 
	 * @param url URL of the VersionOne server
	 * @param userName VersionOne username
	 * @param password VersionOne password
	 * @param proxy Proxy for the connection
	 */
	public V1APIConnector(String url, String userName, String password, ProxyProvider proxy) {

		this.proxy = proxy;
		_url = url;
		cookiesManager = CookiesManager.getCookiesManager(url, userName, password);

		// WORKAROUND: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6626700
		if (userName != null) {
			AuthCacheValue.setAuthCache(new AuthCacheImpl());
			Authenticator.setDefault(new Credentials(userName, password));
		}
		
		//Set a default user-agent header
		setUserAgentHeader(null, null);
	}
	
	/**
	 * Create a connection using a VersionOne access token. If not using a proxy, pass null for the proxy parameter.
	 * 
	 * @param url URL of the VersionOne server
	 * @param accessToken VersionOne access token
	 */
	public V1APIConnector(String url, String accessToken) {
		_url = url;
		_accessToken = accessToken;
		cookiesManager = CookiesManager.getCookiesManager(url, accessToken, accessToken);
		setUserAgentHeader(null, null);
	}

	/**
	 * Set a value for custom the user-agent header.
	 * 
	 * @param name String
	 * @param version String
	 */
	public void setUserAgentHeader(String name, String version) {
		
		_app_name = name;
		_app_version = version;
		String header = "";
		Package p = this.getClass().getPackage();

		header = "Java/" + System.getProperty("java.version") + " " + p.getImplementationTitle() + "/" + p.getImplementationVersion();

		if (!V1Util.isNullOrEmpty(_app_name) && !V1Util.isNullOrEmpty(_app_version)) {
			header = header + " " + _app_name + "/" + _app_version;
		}
		_user_agent_header = header;
	}

	/**
	 * Get the value for the user-agent header.
	 * 
	 * @return String
	 */
	public String getUserAgentHeader() {
		return _user_agent_header;
	}

	/**
	 * Returns a cookies jar.
	 * 
	 * @return ICookiesManager
	 */
	public ICookiesManager getCookiesJar() {
		return cookiesManager;
	}

	/**
	 * Read data from the root of the connection.
	 *
	 * Note: Caller is responsible for closing the returned stream.
	 *
	 * @return The stream for reading data.
	 * @throws ConnectionException - ConnectionException
	 */
	public Reader getData() throws ConnectionException {
		return getData("");
	}

	/**
	 * Read data from the path provided.
	 *
	 * Note: Caller is responsible for closing the returned stream.
	 *
	 * @param path - String
	 * @return The stream for reading data.
	 * @throws ConnectionException - ConnectionException
	 * @return Reader - Reader
	 */
	public Reader getData(String path) throws ConnectionException {

		HttpURLConnection connection = createConnection(_url + path);
		try {
			switch (connection.getResponseCode()) {

			case 200:
				cookiesManager.addCookie(connection.getHeaderFields());
				return new InputStreamReader(connection.getInputStream(), UTF8);

			case 401:
				throw new SecurityException();

			default: {
				StringBuffer message = new StringBuffer("Received Error ");
				message.append(connection.getResponseCode());
				message.append(" from URL ");
				message.append(connection.getURL().toString());
				throw new ConnectionException(message.toString(), connection.getResponseCode());
			}
			}
		} catch (IOException e) {
			throw new ConnectionException("Error processing result from URL " + _url + path, e);
		}
	}

	/**
	 * Send data to the path.
	 *
	 * Note: Caller is responsible for closing the returned stream.
	 *
	 * @param path String
	 * @param data String
	 * @return the response in a stream
	 * @throws ConnectionException  - ConnectionException
	 */
	public Reader sendData(String path, String data) throws ConnectionException {

		HttpURLConnection connection = createConnection(_url + path);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "text/xml");
		OutputStreamWriter stream = null;
		InputStream resultStream = null;

		try {
			connection.setRequestMethod("POST");
			stream = new OutputStreamWriter(connection.getOutputStream(), UTF8);
			stream.write(data);
			stream.flush();
			resultStream = connection.getInputStream();
			cookiesManager.addCookie(connection.getHeaderFields());
		} catch (IOException e) {
			int code;
			try {
				code = connection.getResponseCode();
			} catch (Exception e1) {
				code = -1;
			}
			throw new ConnectionException("Error writing to output stream", code, e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
					// Do nothing
				}
			}
		}
		return new InputStreamReader(resultStream);
	}

	/**
	 * Beginning HTTP POST request to the server.
	 * 
	 * To begin POST request contentType parameter must be defined. If contentType parameter is null, GET request used.
	 * It's obligatory to complete request and get response by {@link #endRequest(String)} method with the same path
	 * parameter.
	 *
	 * @param path Path to the data on server.
	 * @param contentType Content-type of output content. If null - GET request used.
	 * @return the stream for writing POST data.
	 * @see #endRequest(String)
	 */
	public OutputStream beginRequest(String path, String contentType) throws ConnectionException {

		OutputStream outputStream = null;
		HttpURLConnection req = createConnection(_url + path);
		if (contentType != null)
			try {
				req.setDoOutput(true);
				req.setDoInput(true);
				req.setUseCaches(false);
				req.setRequestProperty("Content-Type", contentType);
				req.setRequestMethod("POST");
				outputStream = req.getOutputStream();
			} catch (IOException e) {
				req.disconnect();
				throw new ConnectionException("Error writing to output stream", e);
			}
		_requests.put(path, req);

		return outputStream;
	}

	/**
	 * Complete the HTTP request and get a response.
	 *
	 * @param path Path to the data on server.
	 * @return The response stream for reading data.
	 * @see #beginRequest(String, String)
	 */
	public InputStream endRequest(String path) throws ConnectionException {

		InputStream resultStream = null;
		HttpURLConnection req = _requests.remove(path);

		try {
			if (req.getDoOutput()) {
				OutputStream writeStream = req.getOutputStream();
				writeStream.flush();
			}
			resultStream = req.getInputStream();
			cookiesManager.addCookie(req.getHeaderFields());
		} catch (IOException e) {
			try {
				throw new ConnectionException("Error writing to output stream", req.getResponseCode(), e);
			} catch (IOException e1) {
				throw new ConnectionException("Error writing to output stream", e);
			}
		}

		return resultStream;
	}

	/**
	 * Creates the HTTP request to the VersionOne server.
	 * 
	 * @param path
	 * @return HttpURLConnection
	 * @throws ConnectionException
	 */
	private HttpURLConnection createConnection(String path) throws ConnectionException {

		HttpURLConnection request;
		
		try {
			URL url = new URL(path);
			
			if (proxy == null) {
				request = (HttpURLConnection) url.openConnection();
			} else {
				request = (HttpURLConnection) url.openConnection(proxy.getProxyObject());
				proxy.addAuthorizationToHeader(request);
			}
			
			String localeName = Locale.getDefault().toString();
			localeName = localeName.replace("_", "-");
			request.setRequestProperty("Accept-Language", localeName);
			request.setRequestProperty("User-Agent", _user_agent_header);
			
			// 1-27-2015 AJB Check if using access tokens, if so, add authorization header.
			if (null != _accessToken) {
				request.setRequestProperty("Authorization","Bearer " + _accessToken);
			}
			
			cookiesManager.addCookiesToRequest(request);
			addHeaders(request);
			
		} catch (MalformedURLException e) {
			throw new ConnectionException("Invalid URL", e);
		} catch (IOException e) {
			throw new ConnectionException("Error Opening Connection", e);
		}
		return request;
	}

	private void addHeaders(HttpURLConnection request) {
		for (String key : customHttpHeaders.keySet()) {
			request.setRequestProperty(key, customHttpHeaders.get(key));
		}
	}

	private class Credentials extends Authenticator {

		PasswordAuthentication _value;

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return _value;
		}

		Credentials(String userName, String password) {
			if (null == password) {
				_value = new PasswordAuthentication(userName, "".toCharArray());
			} else {
				_value = new PasswordAuthentication(userName, password.toCharArray());
			}
		}

		@Override
		public String toString() {
			return _value.getUserName() + ":" + String.valueOf(_value.getPassword());
		}
	}
}
