package com.versionone.apiclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.management.OperationsException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.message.BasicHeader;

import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.querybuilder.interfaces.IFluentQueryBuilder;
import com.versionone.utils.V1Util;

public class V1Connector {

	private final String contentType = "text/xml";
	private CredentialsProvider credsProvider = new BasicCredentialsProvider();
	private CloseableHttpResponse httpResponse = null;
	private HttpClientBuilder httpclientBuilder = HttpClientBuilder.create();
	private CloseableHttpClient httpclient;
	private Header[] headerArray = {};
	private HttpPost httpPost;
	private boolean isWindowsAuth = false;

	private final Map<String, OutputStream> _pendingStreams = new HashMap<String, OutputStream>();
	private final Map<String, String> _pendingContentTypes = new HashMap<String, String>();

	// LOCAL VARIABLES
	URL INSTANCE_URL;
	String _endpoint = "";
	String _user_agent_header = "";
	String _upstreamUserAgent = "";

	// VERSIONONE ENDPOINTS
	private final static String META_API_ENDPOINT = "meta.v1/";
	private final static String DATA_API_ENDPOINT = "rest-1.v1/Data/";
	private final static String NEW_API_ENDPOINT = "rest-1.v1/New/";
	private final static String HISTORY_API_ENDPOINT = "rest-1.v1/Hist/";
	private final static String QUERY_API_ENDPOINT = "query.v1/";
	private final static String LOC_API_ENDPOINT = "loc.v1/";
	private final static String LOC2_API_ENDPOINT = "loc-2.v1/";
	private final static String CONFIG_API_ENDPOINT = "config.v1/";
	private final static String ATTACHMENT_API_ENDPOINT = "attachment.img/";
    private final static String EMBEDDED_API_ENDPOINT = "embedded.img/";

	private String restApiUrl;
	public  String getRestApiUrl() { return INSTANCE_URL + _endpoint; } 
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String password;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String accessToken; 

    

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	// INTERFACES
	public interface IsetEndpoint {
		/**
		 * @deprecated This method has been deprecated. 
		 */
		@Deprecated
		IsetProxyOrConnector useEndpoint(String endpoint);
		
	}

	public interface IsetProxyOrConnector extends IBuild {
		/**
		 * Optional method for setting the proxy credentials.
		 * 
		 * @param proxyProvider The ProxyProvider containing the proxy URI, username, and password.
		 * @return IBuild IBuild
		 */
		IBuild withProxy(ProxyProvider proxyProvider);
	}

	public interface IsetEndPointOrConnector extends IBuild {
	
		/**
		 * Optional method for specifying an API endpoint to connect to.
		 * @param endpoint String
		 * @return IBuild IBuild
		 */
		IBuild useEndpoint(String endpoint);
	}

	public interface IsetProxyOrEndPointOrConnector extends IsetEndpoint, IBuild {
		/**
		 * Optional method for setting the proxy credentials.
		 * 
		 * @param proxyProvider  ProxyProvider The ProxyProvider containing the proxy URI, username, and password.
		 * @return IsetEndPointOrConnector IsetEndPointOrConnector
		 */
		IsetEndPointOrConnector withProxy(ProxyProvider proxyProvider);
		
		/**
		 * Fluent Query Builder Definition 
		 * @throws IOException 
		 * @throws UnsupportedOperationException 
		 * @throws ClientProtocolException 
		 * @throws NullArgumentException 
		 * @throws OperationsException 
		 * 
		 * 
		 */
		IFluentQueryBuilder query(String assetTypeName) throws NullArgumentException, ClientProtocolException, UnsupportedOperationException, IOException, OperationsException;

//		IAssetBase create(String assetTypeName, Object attributes );
//
//		IAssetBase update(String oidToken, Object attributes);
//
//		IAssetBase update(IAssetBase asset);
	}

	public interface ISetUserAgentMakeRequest {
		/**
		 * Required method for setting a custom user agent header for all HTTP requests made to the VersionOne API.
		 * 
		 * @param name  String The name of the application.
		 * @param version String The version number of the application
		 * @return IAuthenticationMethods IAuthenticationMethods
		 * @throws V1Exception V1Exception 
		 */
		IAuthenticationMethods withUserAgentHeader(String name, String version) throws V1Exception;
	}

	public interface IAuthenticationMethods {
		
		/**
		 *  Optional method for setting the username and password for authentication.
		 * @param username String
		 * @param password String
		 * @return IsetProxyOrEndPointOrConnector IsetProxyOrEndPointOrConnector
		 * @throws V1Exception V1Exception
		 */
		IsetProxyOrEndPointOrConnector withUsernameAndPassword(String username, String password) throws V1Exception;

		/**
		 * Optional method for setting the Windows Integrated Authentication credentials for authentication based on the
		 * currently logged in user.
		 * @return IsetProxyOrEndPointOrConnector IsetProxyOrEndPointOrConnector
		 * @throws V1Exception V1Exception
		 */
		IsetProxyOrEndPointOrConnector withWindowsIntegrated() throws V1Exception;

		/**
		 * Optional method for setting the Windows Integrated Authentication credentials for authentication based on
		 * specified user credentials.
		 * 
		 * @param accessToken The access token. 
		 * @return IsetProxyOrEndPointOrConnector IsetProxyOrEndPointOrConnector
		 * @throws V1Exception V1Exception
		 */
		IsetProxyOrEndPointOrConnector withAccessToken(String accessToken) throws V1Exception;

	}

	public interface IProxy extends IBuild {
		/**
		 * Optional method for setting the proxy credentials.
		 * 
		 * @param proxyProvider The ProxyProvider containing the proxy URI, username, and password.
		 * @return IBuild IBuild
		 * @throws V1Exception V1Exception
		 */
		IBuild withProxy(ProxyProvider proxyProvider) throws V1Exception;
	}

	// Get the connector (terminating fluent builder method).
	public interface IBuild {
		/**
		 * Required terminating method that returns the V1Connector object.
		 * 
		 * @return V1Connector
		 */
		V1Connector build();
	}

	protected V1Connector(String instanceUrl) throws V1Exception, MalformedURLException {

		if (V1Util.isNullOrEmpty(instanceUrl)) {
			throw new NullPointerException("The VersionOne instance URL cannot be null or empty.");
		}

		// Ensure that we have a forward slash at the end of the V1 instance URL.
		if (!StringUtils.endsWith(instanceUrl, "/"))
			instanceUrl += "/";

		// Validates the V1 instance URL, throws MalformedURLException exception when invalid.
		//URL urlData = 
		INSTANCE_URL = new URL(instanceUrl);
	}

	public static ISetUserAgentMakeRequest withInstanceUrl(String instanceUrl) throws V1Exception, MalformedURLException {
		return new Builder(instanceUrl);
	}

	// // Fluent BUILDER ///
	private static class Builder implements ISetUserAgentMakeRequest, IAuthenticationMethods, IsetProxyOrEndPointOrConnector, IsetProxyOrConnector, IsetEndPointOrConnector {

		private V1Connector v1Connector_instance;

		// builder constructor
		public Builder(String url) throws V1Exception, MalformedURLException {
			v1Connector_instance = new V1Connector(url);
		}

		// set the user agent header
		@Override
		public IAuthenticationMethods withUserAgentHeader(String name, String version) throws V1Exception {

			if (V1Util.isNullOrEmpty(name) || V1Util.isNullOrEmpty(version)) {
				throw new NullPointerException("UserAgent header values cannot be null or empty.");
			}

			Package p = this.getClass().getPackage();
			String headerString = "Java/" + System.getProperty("java.version") + " " + p.getImplementationTitle() + "/"
					+ p.getImplementationVersion();

			Header header = new BasicHeader(HttpHeaders.USER_AGENT, headerString);
			v1Connector_instance.headerArray = (Header[]) ArrayUtils.add(v1Connector_instance.headerArray, header);
			v1Connector_instance.isWindowsAuth = false;

			return this;
		}

		// set the authentication type (if required by the endpoint)
		@Override
		public IsetProxyOrEndPointOrConnector withUsernameAndPassword(String username, String password) throws V1Exception {

			if (V1Util.isNullOrEmpty(username) || V1Util.isNullOrEmpty(username))
				throw new NullPointerException("Username and password values cannot be null or empty.");

			v1Connector_instance.credsProvider.setCredentials(new AuthScope(v1Connector_instance.INSTANCE_URL.getHost(), v1Connector_instance.INSTANCE_URL.getPort()),
					new UsernamePasswordCredentials(username, password));
			v1Connector_instance.httpclientBuilder.setDefaultCredentialsProvider(v1Connector_instance.credsProvider);
			v1Connector_instance.isWindowsAuth = false;

			return this;
		}

		// set the access token
		@Override
		public IsetProxyOrEndPointOrConnector withAccessToken(String accessToken) throws V1Exception {

			if (V1Util.isNullOrEmpty(accessToken))
				throw new NullPointerException("Access token value cannot be null or empty.");

			Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			v1Connector_instance.headerArray = (Header[]) ArrayUtils.add(v1Connector_instance.headerArray, header);
			v1Connector_instance.isWindowsAuth = false;
			
			v1Connector_instance.setAccessToken(accessToken);
			
			return this;
		}

		@Override
		public IsetProxyOrEndPointOrConnector withWindowsIntegrated() throws V1Exception {

			v1Connector_instance.httpclient = WinHttpClients.createDefault();
			v1Connector_instance.isWindowsAuth = true;

			return this;
		}

		@Override
		public IsetEndPointOrConnector withProxy(ProxyProvider proxyProvider) {

			if (null == proxyProvider) {
				throw new NullPointerException("ProxyProvider value cannot be null or empty.");
			}

			v1Connector_instance.credsProvider.setCredentials(new AuthScope(proxyProvider.getAddress().getHost(), proxyProvider.getAddress().getPort()),
					new UsernamePasswordCredentials(proxyProvider.getUserName(), proxyProvider.getPassword()));

			HttpHost proxy = new HttpHost(proxyProvider.getAddress().getHost(), proxyProvider.getAddress().getPort());
			v1Connector_instance.httpclientBuilder.setDefaultCredentialsProvider(v1Connector_instance.credsProvider).setProxy(proxy);
			v1Connector_instance.isWindowsAuth = false;

			return this;
		}

		@Override
		public IsetProxyOrConnector useEndpoint(String endpoint) {

			if (V1Util.isNullOrEmpty(endpoint)) {
				throw new NullPointerException("Endpoint value cannot be null or empty.");
			}

			v1Connector_instance._endpoint = endpoint;
			return this;
		}
		
		@Override
		public V1Connector build() {
			return v1Connector_instance;
		}

		@Override
		public IFluentQueryBuilder query(String assetTypeName) throws NullArgumentException, OperationsException  {
			v1Connector_instance.useDataAPI();
			return new Services(this.build()).query(assetTypeName);
		}
	}
	// end builder

	protected Reader getData() throws ConnectionException {
		return getData("");
	}

	protected Reader getData(String path) throws ConnectionException {
		
		Reader data = null;
		HttpEntity entity = setGETMethod(path);
		int errorCode = httpResponse.getStatusLine().getStatusCode();
		String errorMessage = "\n" + httpResponse.getStatusLine() + " error code: " + errorCode;

		if (errorCode == HttpStatus.SC_OK) {
			try {
				data = new InputStreamReader(entity.getContent());
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			manageErrors( errorCode, errorMessage);
		}
		return data;
	}
	
	protected InputStream getAttachment(String path) throws ConnectionException {
		InputStream data = null;
		HttpEntity entity = setGETMethod(path);
		int errorCode = httpResponse.getStatusLine().getStatusCode();
		String errorMessage = "\n" + httpResponse.getStatusLine() + " error code: " + errorCode;

		if (errorCode == HttpStatus.SC_OK) {
			try {
				data = entity.getContent();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			manageErrors( errorCode, errorMessage);
		}
		return data;
	}

	private HttpEntity setGETMethod(String path) {
		
		String url = V1Util.isNullOrEmpty(path) ? INSTANCE_URL + _endpoint : INSTANCE_URL + _endpoint + path;

		HttpGet request = new HttpGet(url);
		setDefaultHeaderValue();
		request.setHeaders(headerArray);

		// Creates a new httpclient if not using NTLM.
		if (!isWindowsAuth) {
			httpclient = httpclientBuilder.build();
		}

		try {
			httpResponse = httpclient.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpEntity entity = httpResponse.getEntity();
		return entity;
	}

	private void manageErrors(int errorCode, String errorMessage) throws ConnectionException {
	
		switch (errorCode) {
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
		boolean uniqueValue = true;
		Header header = new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, localeName);
		for (Header header2 : headerArray) {
			if (header2.getValue().equals(header.getValue()) && header2.getName().equals(header.getName())) {
					uniqueValue = false;
					break;
			}
		}
		if(uniqueValue)
			headerArray = (Header[]) ArrayUtils.add(headerArray, header);
	}
	
	protected Reader sendData(String path, Object data) throws ConnectionException {
		return sendData(path, data, contentType);
	}

	private HttpPost setPostHeader(String path, String contentType) {
		String url = V1Util.isNullOrEmpty(path) ? INSTANCE_URL + _endpoint : INSTANCE_URL + _endpoint + path;
		HttpPost httpPost = new HttpPost(url);
		String localeName = Locale.getDefault().toString();
		localeName = localeName.replace("_", "-");
		Header header = new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, localeName);
		Header header2 = new BasicHeader(HttpHeaders.CONTENT_TYPE, contentType);
		httpPost.setHeaders((Header[]) ArrayUtils.addAll(headerArray, new Header[] { header, header2 }));
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

		if (!isWindowsAuth) {
			httpclient = httpclientBuilder.build();
		}
		try {
			httpResponse = httpclient.execute(httpPost);
			resultStream = IOUtils.toString(httpResponse.getEntity().getContent());
		} catch (IOException ex) {
			int code;
			try {
				code = httpResponse.getStatusLine().getStatusCode();
			} catch (Exception e1) {
				code = -1;
			}
			try {
				throw new ConnectionException("Error writing to output stream", code, ex);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
		return resultStream;
	}

	public Reader sendData(String key, Object data, String contentType) {
		
		Reader resultStream = null;
		Object newData = null;
		Object xmlPayload = null;

		httpPost = setPostHeader(key, contentType);

		if (data instanceof byte[]) {
			newData = (byte[]) data;
			xmlPayload = new ByteArrayEntity((byte[]) newData);
			httpPost.setEntity((HttpEntity) xmlPayload);
		} else if (data instanceof String) {
			newData = (String) data;
			try {
				xmlPayload = new StringEntity((String) newData);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			httpPost.setEntity((HttpEntity) xmlPayload);
		}

		if (!isWindowsAuth) {
			httpclient = httpclientBuilder.build();
		}
		
		try {
			httpResponse = httpclient.execute(httpPost);
			resultStream = new InputStreamReader(httpResponse.getEntity().getContent());
		} catch (IOException ex) {
			int code;
			try {
				code = httpResponse.getStatusLine().getStatusCode();
			} catch (Exception e1) {
				code = -1;
			}
			try {
				throw new ConnectionException("Error writing to output stream", code, ex);
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
		
		
		return resultStream;
	}

	protected OutputStream beginRequest(String path, String contentType) throws ConnectionException {
		OutputStream outputStream = new ByteArrayOutputStream();
		_pendingStreams.put(path, outputStream);
		return outputStream;
	}

	protected InputStream endRequest(String path) throws ConnectionException {
		
		OutputStream os = _pendingStreams.get(path);
		_pendingStreams.remove(path);
		String ct = _pendingContentTypes.get(path);
		_pendingContentTypes.remove(path);
		byte[] data = ((ByteArrayOutputStream) os).toByteArray();

		sendData(path, data, ct);
		return null;
	}
	
	
//	public String getUserAgent
//	{
//			var assembly = Assembly.GetAssembly(typeof(V1Connector));
//
//			return FormatAssemblyUserAgent(assembly, _upstreamUserAgent);
//		}
//	}
//
//	private string FormatAssemblyUserAgent(Assembly a, string upstream = null)
//	{
//		if (a == null) return null;
//		var n = a.GetName();
//		var s = String.Format("{0}/{1} ({2})", n.Name, n.Version, n.FullName);
//		if (!String.IsNullOrEmpty(upstream))
//			s = s + " " + upstream;
//		return s;
//	}

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
	
	public void useAttachmentApi()
    {
        _endpoint = ATTACHMENT_API_ENDPOINT;
    }

	public void useEmbeddedApi()
    {
        _endpoint =  EMBEDDED_API_ENDPOINT;
    }

	public Object getUserAgent() {
		// TODO Auto-generated method stub
		return null;
	}

}
