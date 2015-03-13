package com.versionone.apiclient.interfaces;


public interface ISetHeaderMakeRequest extends IAPIConnector {
	
	   void setUpstreamUserAgent(String userAgent);

	   IAPIConnector withUserAgentHeader(String name, String version);

}
