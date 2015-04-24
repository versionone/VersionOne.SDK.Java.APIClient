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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.utils.V1Util;



public class V1Connector {

	private static final String UTF8 = "UTF-8";

	private static Logger log = Logger.getLogger(V1Connector.class);
	private static CredentialsProvider credsProvider = new BasicCredentialsProvider();
	private static CloseableHttpResponse httpResponse = null;
	private static HttpClientBuilder httpclientBuilder = HttpClientBuilder.create();
	private static CloseableHttpClient httpclient;
	private static HttpGet request;
	private static Header[] headerArray = {}; 

	// local variables
	static String _url = "";
	static String _endpoint = "";
	static String _user_agent_header = "";
	static String _upstreamUserAgent = "";
	// ENDPOINTS
	private final static String META_API_ENDPOINT = "meta.v1/";
	private final static String DATA_API_ENDPOINT = "rest-1.v1/Data/";
	private final static String NEW_API_ENDPOINT = "rest-1.v1/New/";
	private final static String HISTORY_API_ENDPOINT = "rest-1.v1/Hist/";
	private final static String QUERY_API_ENDPOINT = "query.v1/";

	// INTERFACES
	public interface IsetEndPoint{
		/**
		 * Optional method for specifying an API endpoint to connect to.
		 * @param endPoint The API endpoint.
		 * @return IsetProxyOrConnector
		 */
		IsetProxyOrConnector useEndPoint(String endPoint);
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
		IBuild useEndPoint(String endPoint);
	}
	
	public interface IsetProxyOrEndPointOrConnector extends IsetEndPoint, IBuild{
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
		IsetProxyOrEndPointOrConnector withOAuth2Token(String oAuth2Token) throws V1Exception;

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

	// get the connector (terminating build method)
	public interface IBuild {
		/**
		 * Required terminating method that returns the V1Connector object.
		 * @return V1Connector
		 */
		V1Connector build();
	}

	protected V1Connector(String url) throws V1Exception, MalformedURLException {
		log.info("called V1Connector construcor ");
		log.info("with url: " + url);
		URL urlData = new URL(url);
		if (!StringUtils.endsWith(url, "/"))
			url += "/";
		this._url = url;
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

			Package p = this.getClass().getPackage();
			String headerString = "Java/" + System.getProperty("java.version") + " " + p.getImplementationTitle() + "/"
					+ p.getImplementationVersion();

			if (!V1Util.isNullOrEmpty(name) && !V1Util.isNullOrEmpty(version)) {
				headerString = headerString + " " + name + "/" + version;
			} else {
				throw new V1Exception("Error processing User Agent name/version: " + name + version);
			}

			Header header = new BasicHeader(HttpHeaders.USER_AGENT, headerString);

			headerArray = (Header[]) ArrayUtils.add(headerArray, header);

			return this;
		}

		// set the authentication type (if required by the endpoint)
		@Override
		public IsetProxyOrEndPointOrConnector withUsernameAndPassword(String username, String password) throws V1Exception {
			log.info("called V1Connector.withUsernameAndPassword ");
			log.info("with username/password: " + username + " / " + password);

			if (V1Util.isNullOrEmpty(username) || V1Util.isNullOrEmpty(username))
				throw new V1Exception("Error processing User Name / Password ");
			URL instanceUri = null;
			try {
				instanceUri = new URL(_url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new V1Exception("Error processing URL " + _url + " " + e.getMessage());
			}
			credsProvider.setCredentials(new AuthScope(instanceUri.getHost(), instanceUri.getPort()), new UsernamePasswordCredentials(username,
					password));
			httpclientBuilder.setDefaultCredentialsProvider(credsProvider);

			return this;
		}

		// set the access token 
		@Override
		public IsetProxyOrEndPointOrConnector withAccessToken(String accessToken) throws V1Exception {
			log.info("called V1Connector.withAccessToken ");
			log.info("with accesstoken: " + accessToken);

			if (V1Util.isNullOrEmpty(accessToken))
				throw new V1Exception("Error processing accessToken Null/Empty ");

			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			headerArray = (Header[]) ArrayUtils.add(headerArray, header);
			return this;
		}
		
		@Override
		public IsetProxyOrEndPointOrConnector withOAuth2Token(String oAuth2Token) throws V1Exception {
			log.info("called V1Connector.withOAth2 ");
			log.info("with accesstoken: " + oAuth2Token);

			if (V1Util.isNullOrEmpty(oAuth2Token))
				throw new V1Exception("Error processing accessToken Null/Empty ");

			 Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oAuth2Token);
			 headerArray = (Header[]) ArrayUtils.add(headerArray, header);
			 return this;
		}

		@Override
		public IsetProxyOrEndPointOrConnector withWindowsIntegrated(String username, String password) throws V1Exception {
			log.info("called V1Connector.withWindowsIntegrated ");
			log.info("with username : " + username);
			log.info("with password: " + password);

			if (V1Util.isNullOrEmpty(username) || V1Util.isNullOrEmpty(username)) {
				// use the logged user to the domain
				throw new V1Exception("Error processing Windows integrated access ");
			}

			String authEncodedString = encodingLoginInfo(username, password);
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(authEncodedString));
			httpclientBuilder.setDefaultCredentialsProvider(credsProvider);

			return this;
		}

		@Override
		public IsetProxyOrEndPointOrConnector withWindowsIntegrated() throws V1Exception {
			log.info("called V1Connector.withWindowsIntegrated ");
			
			String fullyQualifiedDomainUsername = 
						new com.sun.security.auth.module.NTSystem().getDomain() + "\\" +  
						new com.sun.security.auth.module.NTSystem().getName();
			
			String authEncodedString = encodingLoginInfo(fullyQualifiedDomainUsername, "");

			Lookup<AuthSchemeProvider> authProviders = RegistryBuilder.<AuthSchemeProvider>create()
			            .register(AuthSchemes.NTLM, new NTLMSchemeFactory())                
			            .build();

			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(authEncodedString));
			httpclientBuilder.setDefaultCredentialsProvider(credsProvider).setDefaultAuthSchemeRegistry(authProviders);

			return this;
		}

		// set the proxy
		@Override
		public IsetEndPointOrConnector withProxy(ProxyProvider proxyProvider)  {
			log.info("called V1Connector.withproxy ");
			log.info("with proxyProvider: " + proxyProvider.toString());

			if (proxyProvider == null) {
				log.error("Proxy Provider is null");
				try {
					throw new V1Exception("Error processing proxy Null/Empty ");
				} catch (V1Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			credsProvider.setCredentials(new AuthScope(proxyProvider.getAddress().getHost(), proxyProvider.getAddress().getPort()),
					new UsernamePasswordCredentials(proxyProvider.getUserName(), proxyProvider.getPassword()));

			httpclientBuilder.setDefaultCredentialsProvider(credsProvider);

			HttpHost proxy = new HttpHost(proxyProvider.getAddress().getHost(), proxyProvider.getAddress().getPort());

			httpclientBuilder.setDefaultCredentialsProvider(credsProvider).setProxy(proxy);
			return this;
		}
	

		@Override
		public IsetProxyOrConnector useEndPoint(String endPoint) {
			log.info("called V1Connector.useEndPoint ");
			log.info("with useEndPoint: " + endPoint);

			if (V1Util.isNullOrEmpty(endPoint))
				try {
					throw new V1Exception("Error processing endPoint Null/Empty ");
				} catch (V1Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			instance._endpoint = endPoint;

			return this;
		}
		
//		@Override
//		IBuild IsetEndPointOrConnector.withProxy(ProxyProvider proxyProvider){
//			return this;
//		}
////		
//		IBuild useEndPoint(String endPoint){
//			return this;
//		}

		
		// build
		@Override
		public V1Connector build() {
			log.info("called V1Connector.connet ");
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
		log.info("called V1Connector.getData ");
		log.info("with : " + path);
		log.info("called V1Connector.getData with _url + _endpoint:  " + _url + _endpoint);
		
		Reader data = null;

		String url = V1Util.isNullOrEmpty(path) ? _url + _endpoint : _url + _endpoint + path;

		HttpGet request = new HttpGet(url);
		
		setDefaultHeaderValue();
		
		request.setHeaders(headerArray);
		
		// creates a new httclient
		httpclient = httpclientBuilder.build();

		try {
			httpResponse = httpclient.execute(request);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		// execute connection
		HttpEntity entity = httpResponse.getEntity();

		int errorCode = httpResponse.getStatusLine().getStatusCode();

		String errorMessage = "\n" + httpResponse.getStatusLine() + "error code: " + errorCode;

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
			throw new ConnectionException(errorMessage + " VersionOne encountered a unexpected error occurred while processing the request.");
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

		InputStream resultStream = null;
		
		String url = V1Util.isNullOrEmpty(path) ? _url + _endpoint : _url + _endpoint + path;

		HttpPost httpPost = new HttpPost(url);
		Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "text/xml");
		headerArray = (Header[]) ArrayUtils.add(headerArray, header);

		StringEntity xmlPayload = null;
		try {
			xmlPayload = new StringEntity(data);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(xmlPayload);
		httpPost.setHeaders(headerArray);
		setDefaultHeaderValue();
		
		// creates a new httclient
		httpclient = httpclientBuilder.build();

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

	protected OutputStream beginRequest(String path, String contentType) throws ConnectionException {

		String url = "";
		OutputStream outputStream = null;

		if (contentType != null)
			
			url = V1Util.isNullOrEmpty(path) ? _url + _endpoint : _url + _endpoint + path;
			HttpPost httpPost = new HttpPost(url);

			Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "text/xml");
			headerArray = (Header[]) ArrayUtils.add(headerArray, header);
			httpPost.setHeaders(headerArray);
			
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
		String url = ""; 
		InputStream resultStream = null;
		
			url = V1Util.isNullOrEmpty(path) ? _url + _endpoint : _url + _endpoint + path;
			HttpPost httpPost = new HttpPost(url);

			Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "text/xml");
			headerArray = (Header[]) ArrayUtils.add(headerArray, header);
			httpPost.setHeaders(headerArray);

			try {
				httpResponse = httpclient.execute(httpPost);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
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

	public void useEndPoint(String endPoint) throws V1Exception {
		if (V1Util.isNullOrEmpty(endPoint))
			throw new V1Exception("Error processing endpoint Null/Empty ");
		_endpoint = endPoint;
	}

}
