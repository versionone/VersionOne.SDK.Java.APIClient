package com.versionone.apiclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.utils.V1Util;



public class V1Connector {

	private static final String UTF8 = "UTF-8";
	private static final String contentType = "text/xml";
	private static Logger log = Logger.getLogger(V1Connector.class);
	private static CredentialsProvider credsProvider = new BasicCredentialsProvider();
	private static CloseableHttpResponse httpResponse = null;
	private static HttpClientBuilder httpclientBuilder = HttpClientBuilder.create();
	private static CloseableHttpClient httpclient;
	private static Header[] headerArray = {}; 
	private static HttpPost httpPost;
	private static boolean isWindowsAuth =  false;

	// LOCAL VARIABLES
	static URL INSTANCE_URL ;
	static String _endpoint = "";
	static String _user_agent_header = "";
	static String _upstreamUserAgent = "";

	// VERSIONONE ENDPOINTS
	private final static String META_API_ENDPOINT = "meta.v1/";
	private final static String DATA_API_ENDPOINT = "rest-1.v1/Data/";
	private final static String NEW_API_ENDPOINT = "rest-1.v1/New/";
	private final static String HISTORY_API_ENDPOINT = "rest-1.v1/Hist/";
	private final static String QUERY_API_ENDPOINT = "query.v1/";
	private final static String LOC_API_ENDPOINT = "loc.v1/"; //TODO: Need to support this endpoint.
	private final static String LOC2_API_ENDPOINT = "loc-2.v1/"; //TODO: Need to support this endpoint.
	private final static String CONFIG_API_ENDPOINT = "config.v1/"; //TODO: Need to support this endpoint.

	// INTERFACES
	public interface IsetEndpoint{
		/**
		 * Optional method for specifying an API endpoint to connect to.
		 * @param endpoint The API endpoint.
		 * @return IsetProxyOrConnector
		 */
		IsetProxyOrConnector useEndpoint(String endpoint);
	}
	
	public interface IsetProxyOrConnector extends IBuild{
		/**
		 * Optional method for setting the proxy credentials.
		 * @param proxyProvider The ProxyProvider containing the proxy URI, username, and password.
		 * @return IBuild
		 */
		IBuild withProxy(ProxyProvider proxyProvider);
	}
	
	public interface IsetEndPointOrConnector extends IBuild{
		/**
		 *  Optional method for specifying an API endpoint to connect to.
		 * @param endPoint The API endpoint.
		 * @return IBuild
		 */
		IBuild useEndpoint(String endpoint);
	}
	
	public interface IsetProxyOrEndPointOrConnector extends IsetEndpoint, IBuild{
		/**
		 * Optional method for setting the proxy credentials.
		 * @param proxyProvider The ProxyProvider containing the proxy URI, username, and password.
		 * @return IsetEndPointOrConnector
		 */
		IsetEndPointOrConnector withProxy(ProxyProvider proxyProvider);
	}

	public interface ISetUserAgentMakeRequest {
		/**
		 * Required method for setting a custom user agent header for all HTTP requests made to the VersionOne API.
		 * @param name The name of the application.
		 * @param version The version number of the application
		 * @return IAuthenticationMethods
		 * @throws V1Exception
		 */
		IAuthenticationMethods withUserAgentHeader(String name, String version) throws V1Exception;
	}

	public interface IAuthenticationMethods {
		/**
		 * Optional method for setting the username and password for authentication.
		 * @param userName The username of a valid VersionOne member account.
		 * @param password The password of a valid VersionOne member account.
		 * @return IProxy
		 * @throws V1Exception
		 */
		IsetProxyOrEndPointOrConnector withUsernameAndPassword(String username, String password) throws V1Exception;

		/**
		 * Optional method for setting the Windows Integrated Authentication credentials for authentication based on the currently logged in user.
		 * @return IProxy
		 * @throws V1Exception
		 */
		IsetProxyOrEndPointOrConnector withWindowsIntegrated() throws V1Exception;

		/**
		 * Optional method for setting the access token for authentication.
		 * @param accessToken The access token.
		 * @return IProxy
		 * @throws V1Exception
		 */
		IsetProxyOrEndPointOrConnector withAccessToken(String accessToken) throws V1Exception;

		/**
		 * Optional method for setting the OAuth2 access token for authentication.
		 * @param accessToken The OAuth2 access token.
		 * @return IProxy
		 * @throws V1Exception
		 */
		IsetProxyOrEndPointOrConnector withOAuth2Token(String oauth2Token) throws V1Exception;

		/**
		 * Optional method for setting the Windows Integrated Authentication credentials for authentication based on specified user credentials.
		 * @param fullyQualifiedDomainUsername The fully qualified domain name in form "DOMAIN\\username".
		 * @param password The password of a valid VersionOne member account.
		 * @return IProxy
		 * @throws V1Exception
		 */
		IsetProxyOrEndPointOrConnector withWindowsIntegrated(String fullyQualifiedDomainUsername, String password) throws V1Exception;
	}

	public interface IProxy extends IBuild {
		/**
		 * Optional method for setting the proxy credentials.
		 * @param proxyProvider The ProxyProvider containing the proxy URI, username, and password.
		 * @return IBuild
		 * @throws V1Exception
		 */
		IBuild withProxy(ProxyProvider proxyProvider) throws V1Exception;
	}

	// Get the connector (terminating fluent builder method).
	public interface IBuild {
		/**
		 * Required terminating method that returns the V1Connector object.
		 * @return V1Connector
		 */
		V1Connector build();
	}

	protected V1Connector(String instanceUrl) throws V1Exception, MalformedURLException {
		
		log.info("called V1Connector construcor ");
		log.info("with url: " + instanceUrl);
		
		if (V1Util.isNullOrEmpty(instanceUrl)) {
			throw new NullPointerException("The VersionOne instance URL cannot be null or empty.");
		}
		
		// Ensure that we have a forward slash at the end of the V1 instance URL.
		if (!StringUtils.endsWith(instanceUrl, "/"))
			instanceUrl += "/";
		
		// Validates the V1 instance URL, throws MalformedURLException exception when invalid.
		@SuppressWarnings("unused")
		URL urlData = new URL(instanceUrl);

		
		INSTANCE_URL = urlData;
	}

	public static ISetUserAgentMakeRequest withInstanceUrl(String instanceUrl) throws V1Exception, MalformedURLException {
		return new Builder(instanceUrl);
	}

	//// Fluent BUILDER ///
	private static class Builder implements ISetUserAgentMakeRequest, IAuthenticationMethods, IsetProxyOrEndPointOrConnector, IsetProxyOrConnector, IsetEndPointOrConnector  {

		private V1Connector instance;

		// builder constructor
		public Builder(String url) throws V1Exception, MalformedURLException {
			log.info("Builder with url: " + url);
			instance = new V1Connector(url);
		}

		// set the user agent header
		@Override
		public IAuthenticationMethods withUserAgentHeader(String name, String version) throws V1Exception {
			log.info("called V1Connector.withUserAgentHeader ");
			log.info("with name/version: " + name + " / " + version);

			if (V1Util.isNullOrEmpty(name) || V1Util.isNullOrEmpty(version)) {
				throw new NullPointerException("UserAgent header values cannot be null or empty.");
			}
			
			Package p = this.getClass().getPackage();
			String headerString = "Java/" + System.getProperty("java.version") + " " + p.getImplementationTitle() + "/" + p.getImplementationVersion();

			Header header = new BasicHeader(HttpHeaders.USER_AGENT, headerString);
			headerArray = (Header[]) ArrayUtils.add(headerArray, header);
			isWindowsAuth=false;

			return this;
		}

		// set the authentication type (if required by the endpoint)
		@Override
		public IsetProxyOrEndPointOrConnector withUsernameAndPassword(String username, String password) throws V1Exception {
			log.info("called V1Connector.withUsernameAndPassword ");
			log.info("with username/password: " + username + " / " + password);

			if (V1Util.isNullOrEmpty(username) || V1Util.isNullOrEmpty(username))
				throw new NullPointerException("Username and password values cannot be null or empty.");
			
			credsProvider.setCredentials(new AuthScope(INSTANCE_URL.getHost(), INSTANCE_URL.getPort()), new UsernamePasswordCredentials(username, password));
			httpclientBuilder.setDefaultCredentialsProvider(credsProvider);
			isWindowsAuth=false;

			return this;
		}

		// set the access token 
		@Override
		public IsetProxyOrEndPointOrConnector withAccessToken(String accessToken) throws V1Exception {
			log.info("called V1Connector.withAccessToken ");
			log.info("with accesstoken: " + accessToken);

			if (V1Util.isNullOrEmpty(accessToken))
				throw new NullPointerException("Access token value cannot be null or empty.");
			
			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			headerArray = (Header[]) ArrayUtils.add(headerArray, header);
			isWindowsAuth=false;
			
			return this;
		}
		
		@Override
		public IsetProxyOrEndPointOrConnector withOAuth2Token(String accessToken) throws V1Exception {
			log.info("called V1Connector.withOAth2 ");
			log.info("with accesstoken: " + accessToken);

			if (V1Util.isNullOrEmpty(accessToken))
				throw new NullPointerException("Access token value cannot be null or empty.");

			 Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			 headerArray = (Header[]) ArrayUtils.add(headerArray, header);
			 isWindowsAuth=false;
			 
			 return this;
		}

		@Override
		public IsetProxyOrEndPointOrConnector withWindowsIntegrated(String fullyQualifiedDomainUsername, String password) throws V1Exception {
			log.info("called V1Connector.withWindowsIntegrated ");
			log.info("with username : " + fullyQualifiedDomainUsername);
			log.info("with password: " + password);
			
			if (V1Util.isNullOrEmpty(fullyQualifiedDomainUsername) || V1Util.isNullOrEmpty(password)) {
				throw new NullPointerException("NTLM credential values cannot be null or empty.");
			}
			
			// Domain/username:password formed string.
			fullyQualifiedDomainUsername += ":" + password;
			credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(fullyQualifiedDomainUsername));
			httpclientBuilder.setDefaultCredentialsProvider(credsProvider);
			isWindowsAuth =  false;
			
			return this;
		}

		@Override
		public IsetProxyOrEndPointOrConnector withWindowsIntegrated() throws V1Exception {
			log.info("called V1Connector.withWindowsIntegrated ");
			
			httpclient =  WinHttpClients.createDefault();
			isWindowsAuth=true;
			
			return this;
		}

		// set the proxy
		@SuppressWarnings("unused")
		@Override
		public IsetEndPointOrConnector withProxy(ProxyProvider proxyProvider)  {
			log.info("called V1Connector.withproxy ");
			log.info("with proxyProvider: " + proxyProvider.toString());

			if (null == proxyProvider) {
				throw new NullPointerException("ProxyProvider value cannot be null or empty.");
			}

			credsProvider.setCredentials(new AuthScope(proxyProvider.getAddress().getHost(), proxyProvider.getAddress().getPort()),
					new UsernamePasswordCredentials(proxyProvider.getUserName(), proxyProvider.getPassword()));

			HttpHost proxy = new HttpHost(proxyProvider.getAddress().getHost(), proxyProvider.getAddress().getPort());
			httpclientBuilder.setDefaultCredentialsProvider(credsProvider).setProxy(proxy);
			isWindowsAuth=false;
			
			return this;
		}
	
		@Override
		public IsetProxyOrConnector useEndpoint(String endpoint) {
			log.info("called V1Connector.useEndpoint ");
			log.info("with useEndpoint: " + endpoint);

			if (V1Util.isNullOrEmpty(endpoint)) {
				throw new NullPointerException("Endpoint value cannot be null or empty.");
			}	
			
			_endpoint = endpoint;
			return this;
		}
		
		// build
		@Override
		public V1Connector build() {
			log.info("called V1Connector.connect ");
			return instance;
		}
	}
	// end builder

	protected Reader getData() throws ConnectionException {
		return getData("");
	}

	protected Reader getData(String path) throws ConnectionException {
		
		log.info("called V1Connector.getData ");
		log.info("with : " + path);
		log.info("called V1Connector.getData with _url + _endpoint:  " + INSTANCE_URL + _endpoint);
		
		Reader data = null;
		String url = V1Util.isNullOrEmpty(path) ? INSTANCE_URL + _endpoint : INSTANCE_URL + _endpoint + path;

		HttpGet request = new HttpGet(url);
		setDefaultHeaderValue();
		request.setHeaders(headerArray);
		
		// Creates a new httpclient if not using NTLM.
		if (!isWindowsAuth){
			httpclient = httpclientBuilder.build();
		}

		try {
			httpResponse = httpclient.execute(request);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		HttpEntity entity = httpResponse.getEntity();
		int errorCode = httpResponse.getStatusLine().getStatusCode();
		String errorMessage = "\n" + httpResponse.getStatusLine() + " error code: " + errorCode;

		switch (errorCode) {
		case HttpStatus.SC_OK:
			try {
				data = new InputStreamReader(entity.getContent());
			} catch (UnsupportedEncodingException ex) {
				log.error(ex.getMessage());
				throw new ConnectionException("Error processing response content unsupported encoding " + ex.getMessage());
			} catch (IllegalStateException ex) {
				log.error(ex.getMessage());
				throw new ConnectionException("Error processing response Illegal state " + ex.getMessage());
			} catch (IOException ex) {
				log.error(ex.getMessage());
				throw new ConnectionException("Error processing response " + ex.getMessage());
			}
			return data;
			
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
			throw new ConnectionException(errorMessage + " VersionOne encountered an unexpected error occurred while processing the request.");
			
		default:
			throw new ConnectionException(errorMessage);
		}
	}

	private void setDefaultHeaderValue() {
		String localeName = Locale.getDefault().toString();
		localeName = localeName.replace("_", "-");
		Header header = new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, localeName);
		headerArray = (Header[]) ArrayUtils.add(headerArray, header);
	}

	protected Reader sendData(String path, String data) throws ConnectionException {
		return sendData(path, data, contentType);
	}
	
	
	protected Reader sendData(String path, String data, String contentType) throws ConnectionException {

		InputStream resultStream = null;
		StringEntity xmlPayload = null;
		
		httpPost = setPostHeader(path, contentType);
		
		try {
			xmlPayload = new StringEntity(data);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(xmlPayload);
		setDefaultHeaderValue();
		
		if (!isWindowsAuth){
			httpclient = httpclientBuilder.build();
		}
		try {
			httpResponse = httpclient.execute(httpPost);
			resultStream = httpResponse.getEntity().getContent();
		} catch (IOException ex) {
			log.error(ex.getMessage());
			int code;
			try {
				code = httpResponse.getStatusLine().getStatusCode();
			} catch (Exception e1) {
				log.error(e1.getMessage());
				code = -1;
			}
			throw new ConnectionException("Error writing to output stream", code, ex);
		}
		return new InputStreamReader(resultStream);
	}

	/**
	 * @param path
	 * @return HttpPost
	 */
	private HttpPost setPostHeader(String path, String contentType) {
		String url = V1Util.isNullOrEmpty(path) ? INSTANCE_URL + _endpoint : INSTANCE_URL + _endpoint + path;
		HttpPost httpPost = new HttpPost(url);
		Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, contentType);
		headerArray = (Header[]) ArrayUtils.add(headerArray, header);
		httpPost.setHeaders(headerArray);
		return httpPost;
	}
	
	
	public String stringSendData(String data, String contentType) {
		String resultStream = null;
		StringEntity xmlPayload = null;
		
		httpPost = setPostHeader("", contentType);
		
		try {
			xmlPayload = new StringEntity(data);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(xmlPayload);
		setDefaultHeaderValue();
		
		if (!isWindowsAuth){
			httpclient = httpclientBuilder.build();
		}
		try {
			httpResponse = httpclient.execute(httpPost);
			resultStream =  IOUtils.toString(httpResponse.getEntity().getContent(), UTF8); 
		} catch (IOException ex) {
			log.error(ex.getMessage());
			int code;
			try {
				code = httpResponse.getStatusLine().getStatusCode();
			} catch (Exception e1) {
				log.error(e1.getMessage());
				code = -1;
			}
			try {
				throw new ConnectionException("Error writing to output stream", code, ex);
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultStream;
	}

	protected OutputStream beginRequest(String path, String contentType) throws ConnectionException {

		OutputStream outputStream = null;

		if (contentType != null)
			httpPost = setPostHeader(path, contentType);
		try {
				httpResponse = httpclient.execute(httpPost);
			} catch (IOException e) {
				try {
					httpResponse.close();
				} catch (IOException e1) {
					throw new ConnectionException("Error writing to output stream", e);
				}
				throw new ConnectionException("Error writing to output stream", e);
			}
			
			try {
				IOUtils.copy(httpResponse.getEntity().getContent(), outputStream);
			} catch (IllegalStateException | IOException e) {
				throw new ConnectionException("Error writing to output stream", e);
			}
			return outputStream;
	}

	protected InputStream endRequest(String path) throws ConnectionException {

		InputStream resultStream = null;
		
		httpPost = setPostHeader(path, contentType);
			try {
				httpResponse = httpclient.execute(httpPost);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				resultStream = httpResponse.getEntity().getContent();
			} catch (IllegalStateException | IOException e) {
				 throw new ConnectionException("Error writing to output stream",
						 httpResponse.getStatusLine().getStatusCode(), e);
			}
		  return resultStream;
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

	public void useLocAPI() {
		_endpoint = LOC_API_ENDPOINT;
	}
	public void useLoc2API() {
		_endpoint = LOC2_API_ENDPOINT;
	}
	public void useConfigAPI() {
		_endpoint = CONFIG_API_ENDPOINT;
	}
	
}
