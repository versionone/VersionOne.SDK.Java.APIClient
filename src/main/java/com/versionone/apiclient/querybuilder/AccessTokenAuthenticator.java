package com.versionone.apiclient.querybuilder;

import org.apache.http.client.methods.HttpGet;

public class AccessTokenAuthenticator {

	private String _accessToken;

	public AccessTokenAuthenticator(String accessToken) {
		this._accessToken = accessToken;
	}

	public String get_accessToken() {
		return _accessToken;
	}

	public void set_accessToken(String _accessToken) {
		this._accessToken = _accessToken;
	}


	public void Authenticate(HttpGet request) {
		request.addHeader("Authorization", "Bearer " + _accessToken);
	}

}
