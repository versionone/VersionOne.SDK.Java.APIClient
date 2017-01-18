package com.versionone.apiclient.querybuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.XMLHandler;
import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;
import com.versionone.apiclient.querybuilder.interfaces.IFluentQueryBuilder;

public class AssetClient {

	private CloseableHttpResponse response = null;
	private String _baseUrl = null;
	private String _accessToken = null;
	private Services _services;

	public AssetClient(String baseUrl, String userName, String password) {

		if (StringUtils.isEmpty(userName)) {
			throw new IllegalArgumentException("userName");
		}
		if (StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("password");
		}

	}

	public AssetClient(String baseUrl, String accessToken) {
		this._baseUrl = baseUrl;
		this._accessToken = accessToken;

		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken");
		}
	}

	public AssetClient(Services services) {
		_services = services;
	}

	public IFluentQueryBuilder query(String assetSource) {
		@SuppressWarnings("unchecked")
		Function<String, List<IAssetBase>> execute = (String query) -> {
			
			Reader dataReader = null;

			//CloseableHttpClient httpclient = HttpClients.createDefault();

			HttpGet request = new HttpGet(this._baseUrl + query);

			request.addHeader("Authorization", "Bearer " + _accessToken);

			List<IAssetBase> assets = new ArrayList<IAssetBase>();
			try {
				
				dataReader = (InputStreamReader)_services.retrieve(query);
				
				//response = httpclient.execute(request);
			
				//dataReader = new InputStreamReader(response.getEntity().getContent(),"UTF-8" );

				Document doc = XMLHandler.buildDocument(dataReader, "");
				
				XPath xPath =  XPathFactory.newInstance().newXPath();

				NodeList assetnodes = (NodeList) xPath.evaluate("Asset", doc.getDocumentElement(), XPathConstants.NODESET);
						
				for (int assetIndex = 0; assetIndex < assetnodes.getLength(); ++assetIndex) {
					
					Element element = (Element) assetnodes.item(assetIndex);
				
					assets.add(new AssetBase(element));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return assets;
		};
		return new FluentQueryBuilder(assetSource, execute);
	}
}
