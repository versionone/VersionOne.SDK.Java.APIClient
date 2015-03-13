package com.versionone.apiclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAPIConnector;
import com.versionone.apiclient.interfaces.IApiMethods;
import com.versionone.apiclient.interfaces.IAuthenticationMethods;
import com.versionone.apiclient.interfaces.ISetHeaderMakeRequest;
import com.versionone.utils.V1Util;

public class V1Connector implements IAuthenticationMethods, IProxy, ISetHeaderMakeRequest {

	private static final String UTF8 = "UTF-8";

	private static Logger log = Logger.getLogger(V1Connector.class.getName());

	private final String META_API_ENDPOINT = "meta.v1/";
	private final String DATA_API_ENDPOINT = "rest-1.v1/Data/";
	private final String NEW_API_ENDPOINT = "rest-1.v1/New/";
	private final String HISTORY_API_ENDPOINT = "rest-1.v1/Hist/";
	private final String QUERY_API_ENDPOINT = "query.v1/";
	private String OAuth2;// inicializar

	private CloseableHttpResponse httpResponse = null;

	private HttpClientBuilder httpRequest = HttpClientBuilder.create();

	private HttpGet httpGet = new HttpGet();

	private String _url = "";
	private String _endpoint = "";
	private String _user_agent_header;
	private String _accessToken;
	private String _oAuth2;
	private ProxyProvider _proxyProvider;
	private String _app_name;
	private String _app_version;
	private String _upstreamUserAgent;

	/**
	 * constructor of the class
	 * 
	 * @param url
	 * @throws V1Exception
	 */
	private V1Connector(String url) throws V1Exception {
		log.trace("called V1Connector constructor");
		log.trace("with string" + url);

		if (!V1Util.isNullOrEmpty(url)) {
			this._url = url;
		} else {
			throw new V1Exception("Error processing url " + url);
		}
	}

	/**
	 * set string url to connect and call the constructor for the class
	 * 
	 * @param url
	 * @return
	 * @throws V1Exception
	 */
	public static IAuthenticationMethods createConnector(String url) throws V1Exception {
		log.trace("called V1Connector CreateConnector");
		log.trace("with string" + url);
		return new V1Connector(url);
	}

	/**
	 * set username and password for authentication
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws V1Exception
	 */
	@Override
	public IProxy withUsernameAndPassword(String username, String password) throws V1Exception {

		log.trace("called V1Connector.withUsernameAndPassword ");
		log.trace("with name/version: " + username + "/" + password);

		if (!V1Util.isNullOrEmpty(username))
			throw new V1Exception("Error processing userName Null/Empty ");

		if (!V1Util.isNullOrEmpty(password))
			throw new V1Exception("Error processing userName Null/Empty ");

		String authString = username + ":" + password;
		byte[] authEncodedBytes = Base64.encodeBase64(authString.getBytes());

		String authEncodedString = new String(authEncodedBytes);

		httpGet.addHeader("Authorization", "Basic " + authEncodedString);

		return this;
	}

	/**
	 * set access token info
	 * 
	 * @param accessToken
	 * @return
	 * @throws V1Exception
	 */
	@Override
	public IProxy withAccessToken(String accessToken) throws V1Exception {

		log.trace("called V1Connector.withAccessToken ");
		log.trace("with accesstoken: " + accessToken);

		if (!V1Util.isNullOrEmpty(accessToken))
			throw new V1Exception("Error processing accessToken Null/Empty ");

		httpGet.addHeader("Authorization", "Bearer " + _accessToken);

		return this;
	}

	/**
	 * set access OAth2 credentials info
	 * 
	 * @param accessToken
	 * @return
	 * @throws V1Exception
	 */
	@Override
	public IProxy withOAuth2(String oAuth2) throws V1Exception {

		log.trace("called V1Connector.withOAth2 ");
		log.trace("with accesstoken: " + oAuth2);

		if (!V1Util.isNullOrEmpty(oAuth2))
			throw new V1Exception("Error processing accessToken Null/Empty ");
		_oAuth2 = oAuth2;
		return this;
	}

	@Override
	public IProxy withWindowsIntegrated(String username, String password) throws V1Exception {

		log.trace("called V1Connector.withWindowsIntegrated ");
		log.trace("with username and password: ");

		CredentialsProvider credsProvider = new BasicCredentialsProvider();

		credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(username, password, "", _url));

		httpRequest.setDefaultCredentialsProvider(credsProvider);

		return this;
	}

	/**
	 * set proxy info
	 * 
	 * @param proxyProvider
	 * @return
	 * @throws V1Exception
	 */
	@Override
	public IApiMethods withProxy(ProxyProvider proxyProvider) throws V1Exception {

		// delete this lines
		String proxyUrl = null;
		int port = 0;

		log.trace("called V1Connector.withOAth2 ");
		log.trace("with accesstoken: " + proxyProvider.toString());

		if (proxyProvider == null) {
			// request = (HttpURLConnection) url.openConnection();
		} else {
			// request = (HttpURLConnection) url.openConnection(proxyProvider.getProxyObject());
			// proxyProvider.addAuthorizationToHeader(request);
		}

		// Set proxy if needed
		HttpHost proxy = new HttpHost(proxyUrl, port);

		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

		httpRequest.setRoutePlanner(routePlanner);

		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

		httpRequest.setDefaultRequestConfig(config);

		return this;
	}

	// Endpoints
	@Override
	public ISetHeaderMakeRequest useMetaAPI() {
		_endpoint = META_API_ENDPOINT;
		return this;
	}

	public ISetHeaderMakeRequest useDataAPI() {
		_endpoint = DATA_API_ENDPOINT;
		return this;
	}

	public ISetHeaderMakeRequest useNewAPI() {
		_endpoint = NEW_API_ENDPOINT;
		return this;
	}

	public ISetHeaderMakeRequest useHistoryAPI() {
		_endpoint = HISTORY_API_ENDPOINT;
		return this;
	}

	public ISetHeaderMakeRequest useQueryAPI() {
		_endpoint = QUERY_API_ENDPOINT;
		return this;
	}

	public ISetHeaderMakeRequest useOAuth2() {
		_endpoint = OAuth2;
		return this;
	}

	public ISetHeaderMakeRequest useEndPoint(String endPoint) {
		_endpoint = endPoint;
		return this;
	}

	@Override
	public IAPIConnector withUserAgentHeader(String name, String version) {
		_app_name = name;
		_app_version = version;
		String header = "";
		Package p = this.getClass().getPackage();
		header = "Java/" + System.getProperty("java.version") + " " + p.getImplementationTitle() + "/" + p.getImplementationVersion();
		if (!V1Util.isNullOrEmpty(_app_name) && !V1Util.isNullOrEmpty(_app_version)) {
			header = header + " " + _app_name + "/" + _app_version;
		}
		_user_agent_header = header;

		return this;
	}

	@Override
	public void setUpstreamUserAgent(String userAgent) {
		_upstreamUserAgent = userAgent;
	}

	public Reader getData() throws ConnectionException {
		return null;// getData("");
	}

	public Reader getData(String path) throws ConnectionException {

		String responseBody = null;

		httpResponse = createConnection(_url + _endpoint);

		HttpEntity entity = httpResponse.getEntity();

		try {
			responseBody = EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int errorCode = httpResponse.getStatusLine().getStatusCode();
		String errorMessage = "\n" + httpResponse.getStatusLine() + "\n" + responseBody;

		switch (errorCode) {
			case HttpStatus.SC_OK:
				return null; // new InputStreamReader(httpResponse.getEntity(), UTF8);;
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

	public Reader sendData(String path, String data) throws ConnectionException {
		return null;
	}

	public OutputStream beginRequest(String path, String contentType) throws ConnectionException {
		return null;
	}

	public InputStream endRequest(String path) throws ConnectionException {
		return null;
	}

	/**
	 * Creates the HTTP request to the VersionOne server.
	 */
	private CloseableHttpResponse createConnection(String url) {

		// validate path

		HttpUriRequest request = new HttpGet(url);

		try {
			String localeName = Locale.getDefault().toString();
			localeName = localeName.replace("_", "-");

			request.addHeader("Accept-Language", localeName);
			request.addHeader("User-Agent", _user_agent_header);

			httpResponse = httpRequest.build().execute(request);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return httpResponse;
	}

	
}
