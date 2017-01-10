package com.versionone.apiclient.querybuilder;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;

public class AccessTokenAuhtenticator {
	 
	private String _accessToken;
	

      public String get_accessToken() {
		return _accessToken;
	}

	public void set_accessToken(String _accessToken) {
		this._accessToken = _accessToken;
	}

	/**
	 * contrusctor
	 * @param accessToken
	 */
	public void AcccessTokenAuthenticator(String accessToken){
          this._accessToken = accessToken;
    }

	
    public void Authenticate( HttpGet request)
    {
          request.addHeader("Authorization", "Bearer " + _accessToken);
    }

}
