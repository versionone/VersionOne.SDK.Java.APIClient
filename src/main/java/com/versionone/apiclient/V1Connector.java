package com.versionone.apiclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.utils.V1Util;

public class V1Connector {

	private static final String UTF8 = "UTF-8";

	private static Logger log = Logger.getLogger(V1Connector.class.getName());

	private static CloseableHttpResponse httpResponse = null;
	private static HttpClientBuilder httpclientBuilder = HttpClientBuilder.create();
	private static CloseableHttpClient httpclient;
	static List<Header> headers = new ArrayList<Header>();

	// local variables
	static String _url = "";
	static String _endpoint = "";
	static String _user_agent_header;
	static String _upstreamUserAgent;
	// ENDPOINTS
	private final static String META_API_ENDPOINT = "meta.v1/";
	private final static String DATA_API_ENDPOINT = "rest-1.v1/Data/";
	private final static String NEW_API_ENDPOINT = "rest-1.v1/New/";
	private final static String HISTORY_API_ENDPOINT = "rest-1.v1/Hist/";
	private final static String QUERY_API_ENDPOINT = "query.v1/";

	// INTERFACES

	public interface ISetUserAgentMakeRequest {
		IAuthenticationMethods withUserAgentHeader(String name, String version) throws V1Exception;
	}

	public interface IApiMethods {

		IProxy useMetaAPI();

		IAuthenticationMethods useDataAPI();

		IAuthenticationMethods useNewAPI();

		IAuthenticationMethods useHistoryAPI();

		IAuthenticationMethods useQueryAPI();

		IAuthenticationMethods useEndPoint(String userAgent);
	}

	public interface IAuthenticationMethods {
		IProxy withUsernameAndPassword(String userName, String password) throws V1Exception;

		IProxy withWindowsIntegrated() throws V1Exception;

		IProxy withAccessToken(String accessToken) throws V1Exception;

		IProxy withOAuth2(String oAuth2) throws V1Exception;

		IProxy withWindowsIntegrated(String userName, String password) throws V1Exception;
	}

	public interface IProxy extends IBuild {
		IBuild withProxy(ProxyProvider proxyProvider) throws V1Exception;
	}

	// get the connector (terminating build method)
	interface IBuild {
		V1Connector build();
	}

	protected V1Connector(String url) throws V1Exception {
		if (V1Util.isNullOrEmpty(url)) {
			throw new V1Exception("Error processing url " + url);
		}
		// VALIDATE URL
		UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
		if (urlValidator.isValid(url) == false) {
			throw new V1Exception("Error processing url " + url);
		}
		this._url = url;
	}

	public static ISetUserAgentMakeRequest withInstanceUrl(String versionOneInstanceUrl) throws V1Exception {
		return new Builder(versionOneInstanceUrl);
	}

	// BUILDER OF FLUENT
	private static class Builder implements IAuthenticationMethods, IProxy, ISetUserAgentMakeRequest {

		private V1Connector instance;

		// builder constructor
		public Builder(String url) throws V1Exception {
			instance = new V1Connector(url);

		}

		// set the user agent header
		@Override
		public IAuthenticationMethods withUserAgentHeader(String name, String version) throws V1Exception {

			Package p = this.getClass().getPackage();
			String headerString = "Java/" + System.getProperty("java.version") + " " + p.getImplementationTitle() + "/"
					+ p.getImplementationVersion();

			if (!V1Util.isNullOrEmpty(name) && !V1Util.isNullOrEmpty(version)) {
				headerString = headerString + " " + name + "/" + version;
			} else {
				throw new V1Exception("Error processing User Agent null/empty" + name + version);
			}

			Header header = new BasicHeader(HttpHeaders.USER_AGENT, headerString);

			instance.headers.add(header);

			return this;
		}

		// set the authentication type (if required by the endpoint)
		@Override
		public IProxy withUsernameAndPassword(String username, String password) throws V1Exception {

			log.trace("called V1Connector.withUsernameAndPassword ");
			log.trace("with name/version: " + username + "/" + password);

			if (V1Util.isNullOrEmpty(username) || V1Util.isNullOrEmpty(username))
				throw new V1Exception("Error processing accessToken Null/Empty ");

			String authEncodedString = encodingLoginInfo(username, password);

			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Basic " + authEncodedString);

			instance.headers.add(header);

			return this;
		}

		@Override
		public IProxy withAccessToken(String accessToken) throws V1Exception {

			log.trace("called V1Connector.withAccessToken ");
			log.trace("with accesstoken: " + accessToken);

			if (!V1Util.isNullOrEmpty(accessToken))
				throw new V1Exception("Error processing accessToken Null/Empty ");

			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

			instance.headers.add(header);

			return this;
		}

		@Override
		public IProxy withWindowsIntegrated(String username, String password) throws V1Exception {

			log.trace("called V1Connector.withWindowsIntegrated ");
			log.trace("with username and password: ");

			if (V1Util.isNullOrEmpty(username) || V1Util.isNullOrEmpty(username)) {
				// use the logued user to the domain
				throw new V1Exception("Error processing Windows integrated access ");
			}

			String authEncodedString = encodingLoginInfo(username, password);

			CredentialsProvider credsProvider = new BasicCredentialsProvider();

			credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(authEncodedString));

			instance.httpclientBuilder.setDefaultCredentialsProvider(credsProvider);

			return this;
		}

		@Override
		public IProxy withWindowsIntegrated() throws V1Exception {

			log.trace("called V1Connector.withWindowsIntegrated ");
			log.trace("with username and password: ");

			String domain = new com.sun.security.auth.module.NTSystem().getDomain();

			String fullyQualifiedDomainUsername = domain + "\"" + new com.sun.security.auth.module.NTSystem().getName();

			String authEncodedString = encodingLoginInfo(fullyQualifiedDomainUsername, "");

			CredentialsProvider credsProvider = new BasicCredentialsProvider();

			credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(authEncodedString));

			instance.httpclientBuilder.setDefaultCredentialsProvider(credsProvider);

			return this;
		}

		@Override
		public IProxy withOAuth2(String oAuth2) throws V1Exception {

			log.trace("called V1Connector.withOAth2 ");
			log.trace("with accesstoken: " + oAuth2);

			if (V1Util.isNullOrEmpty(oAuth2))
				throw new V1Exception("Error processing accessToken Null/Empty ");
			// TODO: Need to define Implementation

			// Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			// instance.headers.add(header);
			return this;
		}

		// set the proxy
		@Override
		public IBuild withProxy(ProxyProvider proxyProvider) throws V1Exception {

			log.trace("called V1Connector.withOAth2 ");
			log.trace("with accesstoken: " + proxyProvider.toString());

			if (proxyProvider == null)
				throw new V1Exception("Error processing proxy Null/Empty ");

			// Set proxy if needed
			HttpHost proxy = new HttpHost(proxyProvider.getAddress().getRawPath(), proxyProvider.getAddress().getPort());

			instance.httpclientBuilder.setProxy(proxy);

			return this;
		}

		// build
		@Override
		public V1Connector build() {
			return instance;
		}

		private String encodingLoginInfo(String username, String password) {
			String authString = username + ":" + password;
			byte[] authEncodedBytes = Base64.encodeBase64(authString.getBytes());

			String authEncodedString = new String(authEncodedBytes);
			return authEncodedString;
		}

	}

	// end builder

	protected Reader getData() throws ConnectionException {
		return getData("");
	}

	protected Reader getData(String path) throws ConnectionException {

		// do we really need the path?? backward compatibility
		String responseBody = null;

		HttpGet request = new HttpGet(_url);

		try {
			httpResponse = createConnection().execute(request);

		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// execute connection
		HttpEntity entity = httpResponse.getEntity();

		try {
			responseBody = EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException e) {
			throw new ConnectionException("Error processing request parse exception " + e.getMessage());
		} catch (IOException e) {
			throw new ConnectionException("Error processing request " + e.getMessage());
		}

		int errorCode = httpResponse.getStatusLine().getStatusCode();

		String errorMessage = "\n" + httpResponse.getStatusLine() + "\n" + responseBody;

		switch (errorCode) {
			case HttpStatus.SC_OK:
				try {
					new InputStreamReader(httpResponse.getEntity().getContent(), UTF8);
				} catch (UnsupportedEncodingException e) {
					throw new ConnectionException("Error processing response content unsupported encoding " + e.getMessage());
				} catch (IllegalStateException e) {
					throw new ConnectionException("Error processing response Illegal state " + e.getMessage());
				} catch (IOException e) {
					throw new ConnectionException("Error processing response " + e.getMessage());
				}
				;
			case HttpStatus.SC_BAD_REQUEST:
				throw new ConnectionException(errorMessage + " VersionOne could not process the request.");
			case HttpStatus.SC_UNAUTHORIZED:
				throw new ConnectionException(errorMessage
						+ " Could not authenticate. The VersionOne credentials may be incorrect or the access tokens may have expired.");
			case HttpStatus.SC_NOT_FOUND:
				throw new ConnectionException(errorMessage + " The requested item may not exist, or the VersionOne server is unavailable.");
			case HttpStatus.SC_METHOD_NOT_ALLOWED:
				throw new ConnectionException(errorMessage + " Only GET and POST methods are supported by VersionOne.");
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				throw new ConnectionException(errorMessage + " VersionOne encountered a unexpected error occurred while processing the request.");
			default:
				throw new ConnectionException(errorMessage);
		}
	}

	/**
	 * Creates the HTTP request to the VersionOne server.
	 */
	private CloseableHttpClient createConnection() {

		String localeName = Locale.getDefault().toString();
		localeName = localeName.replace("_", "-");
		Header header = new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, localeName);
		headers.add(header);
		// add all headers settings
		httpclientBuilder.setDefaultHeaders(headers);

		// creates a new httclient
		httpclient = httpclientBuilder.build();

		return httpclient;
	}

	protected Reader sendData(String path, String data) throws ConnectionException {

		OutputStreamWriter stream = null;
		InputStream resultStream = null;

		HttpPost httpPost = new HttpPost(_url);

		httpPost.setHeader("Content-Type", "text/xml");

		try {

			httpResponse = createConnection().execute(httpPost);
//			stream = new OutputStreamWriter(httpResponse.getEntity().getOutputStream(), UTF8);
//			stream.write(data);
//			stream.flush();

			resultStream = httpResponse.getEntity().getContent();

		} catch (IOException e) {
			int code;
			try {
				code = httpResponse.getStatusLine().getStatusCode();
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

	protected OutputStream beginRequest(String path, String contentType) throws ConnectionException {
		return null;
	}

	protected InputStream endRequest(String path) throws ConnectionException {
		return null;
	}

	// endpoint definition
	public void useMetaAPI() {
		_endpoint = META_API_ENDPOINT;
	}

	public void useDataAPI() {
		_endpoint = DATA_API_ENDPOINT;
	}

	public void useNewAPI() {
		_endpoint = NEW_API_ENDPOINT;
	}

	public void useHistoryAPI() {
		_endpoint = HISTORY_API_ENDPOINT;
	}

	public void useQueryAPI() {
		_endpoint = QUERY_API_ENDPOINT;
	}

	public void useEndPoint(String endPoint) throws V1Exception {
		if (V1Util.isNullOrEmpty(endPoint))
			throw new V1Exception("Error processing endpoint Null/Empty ");
		_endpoint = endPoint;
	}

}
