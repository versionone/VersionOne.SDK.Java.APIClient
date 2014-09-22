package com.versionone.apiclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

import java.io.*;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.versionone.util.V1Util.isNullOrEmpty;

/**
 * This class represents a connection to the VersionOne server.
 *
 * @author Jerry D. Odenwelder Jr.
 */
public class V1APIConnector implements IAPIConnector {
    private static final Logger log = LoggerFactory.getLogger(V1APIConnector.class);

    private final CookiesManager cookiesManager;
    private final ICredentials credentials;
    private String _url = null;
    private ProxyProvider proxy = null;
    private final Map<String, HttpURLConnection> _requests = new HashMap<String, HttpURLConnection>();
    /**
     * Additional headers for request to the VersionOne server
     */
    public final Map<String, String> customHttpHeaders = new HashMap<String, String>();

    /**
     * Create Connection.
     * @param url - URL to VersionOne system.
     * @param credentials - Authentication credentials
     */
    public V1APIConnector(String url, ICredentials credentials) {
        this(url, credentials, null);
    }

    /**
     * Create Connection.
     * @param url - URL to VersionOne system.
     * @param credentials - Authentication credentials
     * @param proxy	- proxy for connection.
     */
    public V1APIConnector(String url, ICredentials credentials, ProxyProvider proxy) {
        this.credentials = credentials;
        this.proxy = proxy;
        _url = url;

        String username = credentials == null ? null : credentials.getV1UserName();
        String password = credentials == null ? null : credentials.getV1Password();

        cookiesManager = CookiesManager.getCookiesManager(url, username, password);

        // WORKAROUND: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6626700
        if (credentials != null && credentials.getV1UserName() != null) {
            AuthCacheValue.setAuthCache(new AuthCacheImpl());
            Authenticator.setDefault(new Credentials(credentials.getV1UserName(), credentials.getV1Password()));
        }
    }

    /**
     * Returns cookies jar
     */
    public ICookiesManager getCookiesJar() {
        return cookiesManager;
    }

    /**
     * Create a connection with only the URL.
     * Use this constructor to access MetaData, which does not require or if you want to use
     * have Windows Integrated Authentication or
     * MetaData does not require the use of credentials
     *
     * @param url - Complete URL to VersionOne system
     */
    public V1APIConnector(String url) {
        this(url, null, null);
    }


    /**
     * Create a connection with only the URL and proxy.
     * Use this constructor to access MetaData, which does not require or if you want to use
     * have Windows Integrated Authentication or
     * MetaData does not require the use of credentials
     *
     * @param url - Complete URL to VersionOne system
     * @param proxy - Proxy for connection.
     */
    public V1APIConnector(String url, ProxyProvider proxy) {
        this(url, null, proxy);
    }

    /**
     * Read data from the root of the connection
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
     * Read data from the path provided
     *
     * Note: Caller is responsible for closing the returned stream
     *
     * @param path
     * @return the stream for reading data
     * @throws IOException
     */
    public Reader getData(String path) throws ConnectionException {
        Response connection = createConnection(_url + path);
        switch (connection.getResponseCode()) {
            case 200:
                cookiesManager.addCookie(connection.getHeaderFields());
                return new StringReader(connection.getBody());

            case 401:
                throw new SecurityException();

            default: {
                StringBuffer message = new StringBuffer("Received Error ");
                message.append(connection.getResponseCode());
                message.append(" from URL ");
                message.append(connection.getUrl());
                throw new ConnectionException(message.toString(), connection.getResponseCode());
            }
        }
    }

    /**
     * Send data to the path
     *
     * Note: Caller is responsible for closing the returned stream
     *
     * @param path
     * @param data
     * @return the response in a stream
     * @throws IOException
     */
    public Reader sendData(String path, String data) throws ConnectionException {
        /*
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
			throw new ConnectionException("Error writing to output stream",
					code, e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
					//Do nothing
				}
			}
		}
		return new InputStreamReader(resultStream);
		*/

        throw new ConnectionException("POST not implemented");
    }

    /**
     * Beginning HTTP request to server.
     * <p/>
     * To begin POST request contentType parameter must be defined. If
     * contentType parameter is null, GET request used. It's obligatory to
     * complete request and get response by {@link #endRequest(String)} method
     * with the same path parameter.
     *
     * @param path        path to the data on server.
     * @param contentType Content-type of output content. If null - GET request
     *                    used.
     * @return the stream for writing POST data.
     * @see #endRequest(String)
     */
    public OutputStream beginRequest(String path, String contentType)
            throws ConnectionException {
        /*
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
				throw new ConnectionException("Error writing to output stream",
						e);
			}
		_requests.put(path, req);

		return outputStream;
		*/

        throw new ConnectionException("POST not implemented");
    }

    /**
     * Completing HTTP request and getting response.
     *
     * @param path path to the data on server.
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
                throw new ConnectionException("Error writing to output stream",
                        req.getResponseCode(), e);
            } catch (IOException e1) {
                throw new ConnectionException("Error writing to output stream",
                        e);
            }
        }

        return resultStream;
    }

    /*
	private HttpURLConnection createConnection(String path)
			throws ConnectionException {
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
			cookiesManager.addCookiesToRequest(request);
			addHeaders(request);
		} catch (MalformedURLException e) {
			throw new ConnectionException("Invalid URL", e);
		} catch (IOException e) {
			throw new ConnectionException("Error Opening Connection", e);
		}
		return request;
	}
	*/

    private Response createConnection(String path) throws ConnectionException {
        try {
            URL url = new URL(path);
            HttpHost target = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());

            HttpClientContext context = HttpClientContext.create();

            if (credentials != null && !isNullOrEmpty(credentials.getDomain())) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(credentials.getV1UserName(),
                        credentials.getV1Password(), null, credentials.getDomain()));
                context.setCredentialsProvider(credsProvider);
            }

            String req = url.getQuery() == null ? url.getPath() : url.getPath() + "?" + url.getQuery();
            HttpGet get = new HttpGet(req);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(target, get, context);

            try {
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                return new Response(body, statusCode, url, response.getAllHeaders());
            }
            finally {
                response.close();
            }
        }
        catch (MalformedURLException e) {
            throw new ConnectionException("Invalid URL", e);
        } catch (ClientProtocolException e) {
            throw new ConnectionException("Request could not be performed", e);
        } catch (IOException e) {
            throw new ConnectionException("Request could not be performed", e);
        }
    }

    class Response {
        private final int responseCode;
        private final String body;
        private final URL url;
        private final Header[] headers;

        public Response(String body, int responseCode, URL url, Header[] headers) {
            this.body = body;
            this.responseCode = responseCode;
            this.url = url;
            this.headers = headers;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public String getBody() {
            return body;
        }

        public URL getUrl() {
            return url;
        }

        public Header[] getHeaders() {
            return headers;
        }

        public Map<String, List<String>> getHeaderFields() {
            Map m = new HashMap();
            for (Header header : headers) {
                m.put(header.getName(), Collections.singletonList(header.getValue()));
            }
            return m;
        }
    }

    private void addHeaders(HttpURLConnection request) {
        for (String key : customHttpHeaders.keySet()) {
            request.setRequestProperty(key, customHttpHeaders.get(key));
        }
    }

    /**
     * Authenticator
     * @author Jerry D. Odenwelder Jr.
     *
     */
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
                _value = new PasswordAuthentication(userName, password
                        .toCharArray());
            }
        }

        @Override
        public String toString() {
            return _value.getUserName() + ":"
                    + String.valueOf(_value.getPassword());
        }
    }
}