/**
 * 
 */
package com.versionone.apiclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * @author JKoberg
 *
 */



public class ApacheHttpAPIConnector implements IAPIConnector {
	private String url;
	private Credentials creds;
	protected DefaultHttpClient httpclient;
	private Map<String,ImmutablePair<String, ByteArrayOutputStream>> startedRequests;
	private ProxyProvider proxy;

	public ApacheHttpAPIConnector( String url) {
		this(url, null, null);
	}
		
	public ApacheHttpAPIConnector( String url, String username, String password) {
		this(url, username, password, null);
	}
	
	public ApacheHttpAPIConnector( String url, String username, String password, ProxyProvider proxy) {
		this.url = url;
		this.creds = new UsernamePasswordCredentials(username, password);
		this.proxy = proxy;
		if(this.proxy != null) {
			throw new NotImplementedException();
		}
		this.httpclient = new DefaultHttpClient();
		this.httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
		this.startedRequests = new HashMap<String, ImmutablePair<String, ByteArrayOutputStream>>();
	}

	/* (non-Javadoc)
	 * @see com.versionone.apiclient.IAPIConnector#getData()
	 */
	@Override
	public Reader getData() throws ConnectionException {
		return getData("");
	}
	
	protected Header[] getCustomHeaders() {
		return new Header[0];
	}
	
	/* (non-Javadoc)
	 * @see com.versionone.apiclient.IAPIConnector#getData(java.lang.String)
	 */
	@Override
	public Reader getData(String path) throws ConnectionException {
		String url = this.url + path;
		HttpGet request = new HttpGet(url);
		request.setHeaders(getCustomHeaders());
		
		HttpResponse response;
		try {
			response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				return new InputStreamReader(instream);
			} else {
				throw new ConnectionException("No entity found in response");
			}
		} catch (ClientProtocolException e) {
			throw new ConnectionException("Error contacting server", e);
		} catch (IOException e) {
			throw new ConnectionException("Error contacting server", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.versionone.apiclient.IAPIConnector#sendData(java.lang.String, java.lang.String)
	 */
	@Override
	public Reader sendData(String path, String data) throws ConnectionException {
		String url = this.url + path;
		HttpPost request = new HttpPost(url);
		StringEntity postbody;
		postbody = new StringEntity(data, ContentType.create("text/xml", "UTF-8"));
		request.setEntity(postbody);
		try {
			HttpResponse response = this.httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				return new InputStreamReader(instream);
			} else {
				throw new ConnectionException("No data returned from server");
			}
		} catch (ClientProtocolException e) {
			throw new ConnectionException("Error contacting server", e);
		} catch (IOException e) {
			throw new ConnectionException("Error contacting server", e);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.versionone.apiclient.IAPIConnector#beginRequest(java.lang.String, java.lang.String)
	 */
	@Override
	public OutputStream beginRequest(String path, String contentType) throws ConnectionException {		
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		ImmutablePair<String, ByteArrayOutputStream>
		  startedRequest = new ImmutablePair<String, ByteArrayOutputStream>(contentType, outstream);
		startedRequests.put(path, startedRequest);
		return outstream;		
	}

	/* (non-Javadoc)
	 * @see com.versionone.apiclient.IAPIConnector#endRequest(java.lang.String)
	 */
	@Override
	public InputStream endRequest(String path) throws ConnectionException {
		ImmutablePair<String, ByteArrayOutputStream> startedRequest = startedRequests.get(path);
		if(startedRequest == null) {
			throw new ConnectionException("Must begin request before ending it");
		}
		startedRequests.remove(path);
		String contentType = startedRequest.left;
		ByteArrayOutputStream outstream = startedRequest.right;
		ByteArrayEntity postbody = new ByteArrayEntity(outstream.toByteArray());
		String url = this.url + path;
		postbody.setContentType(contentType);
		HttpPost request = new HttpPost(url);
		request.setEntity(postbody);
		try {
			HttpResponse response = this.httpclient.execute(request);
			HttpEntity responsebody = response.getEntity();
			if(responsebody == null) {
				return new ByteArrayInputStream(new byte[0]);
			}
			return responsebody.getContent();
		} catch (IOException e) {
			throw new ConnectionException("Unable to send attachment", e);
		}
	}
}
