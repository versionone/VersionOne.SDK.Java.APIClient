package com.versionone.apiclient;

import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IApiMethods;

public interface IProxy extends IApiMethods{
	
	 IApiMethods withProxy(ProxyProvider proxyProvider) throws V1Exception;

}
