package com.versionone.apiclient.interfaces;

import com.versionone.apiclient.IProxy;
import com.versionone.apiclient.exceptions.V1Exception;

public interface IAuthenticationMethods extends IApiMethods {

	public IProxy withUsernameAndPassword(String userName, String password) throws V1Exception;

	public IProxy withAccessToken(String accessToken) throws V1Exception;

	public IProxy withOAuth2(String oAuth2) throws V1Exception;

	public IProxy withWindowsIntegrated(String userName, String password) throws V1Exception;

}
