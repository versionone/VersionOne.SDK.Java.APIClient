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

import org.apache.commons.lang3.StringUtils;

import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

/**
 * This class represents a connection to the VersionOne server.
 */
@SuppressWarnings("restriction")
public class V1APIConnector implements IAPIConnector {

	private static final String UTF8 = "UTF-8";

	private final CookiesManager cookiesManager;
	private String _url = null;
	private ProxyProvider proxy = null;
	private final Map<String, HttpURLConnection> _requests = new HashMap<String, HttpURLConnection>();

	/**
	 * Additional headers for request to the VersionOne server.
	 */
	public final Map<String, String> customHttpHeaders = new HashMap<String, String>();

	/**
	 * Additional User-Agent header for request to the VersionOne server.
	 */
	private String _user_agent_header = "";

	private static String _app_name;

	private static String _app_version;

	/**
	 * Create Connection.
	 * 
	 * @param url
	 *            - URL to VersionOne system.
	 * @param userName
	 *            - Name of the user wishing to connect.
	 * @param password
	 *            - password of the user wishing to connect.
	 */
	public V1APIConnector(String url, String userName, String password) {
		this(url, userName, password, null);
	}

	/**
	 * Create Connection.
	 * 
	 * @param url
	 *            - URL to VersionOne system.
	 * @param userName
	 *            - Name of the user wishing to connect.
	 * @param password
	 *            - password of the user wishing to connect.
	 * @param proxy
	 *            - proxy for connection. it is not used ??
	 * 
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
		setUserAgentHeader(null, null);
	}

	/**
	 * Set the value to use for the custom user-agent header.
	 * 
	 * @return String
	 */
	public void setUserAgentHeader(String name, String version) {
		_app_name = name;
		_app_version = version;
		String header = "";
		Package p = this.getClass().getPackage();

		header = "Java/" + System.getProperty("java.version") + " " + p.getImplementationTitle() + "/" + p.getImplementationVersion();

		if (StringUtils.isNotBlank(_app_name) && StringUtils.isNotBlank(_app_version)) {
			header = header + " " + _app_name + "/" + _app_version;
		}
		_user_agent_header = header;
	}

	/**
	 * get the value to use for the custom user-agent header.
	 * 
	 * @return String
	 */
	public String getUserAgentHeader() {

		return _user_agent_header;
	}

	/**
	 * Returns a cookies jar.
	 */
	public ICookiesManager getCookiesJar() {
		return cookiesManager;
	}

	/**
	 * Create a connection with only the URL.
	 * 
	 * Use this constructor to access MetaData, which does not require or if you want to use have Windows Integrated
	 * Authentication or MetaData does not require the use of credentials
	 *
	 * @param url
	 *            - Complete URL to VersionOne system
	 */
	public V1APIConnector(String url) {
		this(url, null, null);
	}

	/**
	 * Create a connection with only the URL and proxy. Use this constructor to access MetaData, which does not require
	 * or if you want to use have Windows Integrated Authentication or MetaData does not require the use of credentials
	 *
	 * @param url
	 *            - Complete URL to VersionOne system
	 * @param proxy
	 *            - Proxy for connection.
	 */
	public V1APIConnector(String url, ProxyProvider proxy) {
		this(url, null, null, proxy);
	}

	/**
	 * Read data from the root of the connection.
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @return the stream for reading data
	 * @throws IOException
	 */
	public Reader getData() throws ConnectionException {
		return getData("");
	}

	/**
	 * Read data from the path provided.
	 *
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @param path
	 * @return the stream for reading data
	 * @throws IOException
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
	 * Note: Caller is responsible for closing the returned stream
	 *
	 * @param path
	 * @param data
	 * @return the response in a stream
	 * @throws IOException
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
	 * @param path
	 *            path to the data on server.
	 * @param contentType
	 *            Content-type of output content. If null - GET request used.
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
	 * Completing HTTP request and getting response.
	 *
	 * @param path
	 *            path to the data on server.
	 * @return the response stream for reading data.
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
