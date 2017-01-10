package com.versionone.apiclient.querybuilder;

import static java.lang.System.out;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;
import com.versionone.apiclient.querybuilder.interfaces.IFluentQueryBuilder;

import sun.net.www.http.HttpClient;

public class AssetClient extends HttpClient {
	
	private CloseableHttpResponse response = null;
	
	public IFluentQueryBuilder query(String assetSource) throws ClientProtocolException, IOException, UnsupportedOperationException
	{
		Function<String, List<IAssetBase>> execute = (String query) ->
		{
			HttpGet request = new HttpGet(query);
			
			CloseableHttpClient httpclient = null;
			
			try {
				response = httpclient.execute(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//need the content??
			  //InputStream results = response.getEntity().getContent();
			
			  ArrayList<String> results = null;
			try {
				results = (ArrayList<String>) IOUtils.readLines(response.getEntity().getContent(), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//var results = response.Data as IList<dynamic>;
			
			List<IAssetBase> assets = new ArrayList<IAssetBase>(results.size());
			
			(results).forEach(item->assets.add(new AssetBase(item, true)));
			
			return assets;
		};
		return new FluentQueryBuilder(assetSource, execute);
	}
}
