package com.versionone.apiclient;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;


public class V1APIConnector extends ApacheHttpAPIConnector {

	public V1APIConnector(String url) {
		super(url);
	}
	
	public V1APIConnector(String url, String username, String password) {
		super(url, username, password);
	}
	
	public V1APIConnector(String url, String username, String password, ProxyProvider proxy) {
		super(url, username, password, proxy);
	}
	
	public V1APIConnector(String url, ProxyProvider proxy) {
		super(url, "admin", "admin", proxy);
	}
	
	
	public ICookiesManager getCookiesJar() {
		throw new NotImplementedException();
	}
	
	public Map<String, String> customHttpHeaders = new HashMap<String, String>();
	
	@Override
	protected  Header[] getCustomHeaders() {
		Header[] headers = new Header[this.customHttpHeaders.size()];
		int i = 0;
		for(String key : this.customHttpHeaders.keySet()) {
			headers[i] = new BasicHeader(key, this.customHttpHeaders.get(key));
		}
		return headers;
	}
	
	
}

