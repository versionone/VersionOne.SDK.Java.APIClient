package com.versionone.apiclient.interfaces;


public interface IApiMethods {

	public ISetHeaderMakeRequest useMetaAPI();

	public ISetHeaderMakeRequest useDataAPI();

	public ISetHeaderMakeRequest useNewAPI();

	public ISetHeaderMakeRequest useHistoryAPI();

	public ISetHeaderMakeRequest useQueryAPI();

	public ISetHeaderMakeRequest useOAuth2();

	public ISetHeaderMakeRequest useEndPoint(String userAgent);

}
