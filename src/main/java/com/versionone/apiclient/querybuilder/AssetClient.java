package com.versionone.apiclient.querybuilder;

import sun.net.www.http.HttpClient;

public class AssetClient extends HttpClient {

	public AssetClient(String restApiUrl, String username, String password) {
		// TODO Auto-generated constructor stub
	}

	public AssetClient(String restApiUrl, String accessToken) {
		// TODO Auto-generated constructor stub
	}

	public IFluentQueryBuilder query(String assetSource)
	{
		Func<string, IList<IAssetBase>> execute = (string query) =>
		{
			var req = new RestRequest(query);
			var response = this.Get<List<dynamic>>(req);
			var results = response.Data as IList<dynamic>;
			var assets = new List<IAssetBase>(results.Count);
			foreach (dynamic item in results)
			{
				assets.Add(new AssetBase(item, true));
			}
			return assets;
		};
		return new FluentQueryBuilder(assetSource, execute);
	}
}
