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

import com.versionone.apiclient.XMLHandler;
import com.versionone.apiclient.querybuilder.interfaces.IAssetBase;
import com.versionone.apiclient.querybuilder.interfaces.IFluentQueryBuilder;

public class AssetClient {

	private CloseableHttpResponse response = null;
	private String _baseUrl = null;
	private String _accessToken = null;

	public AssetClient(String baseUrl, String userName, String password) {

		if (StringUtils.isEmpty(userName)) {
			throw new IllegalArgumentException("userName");
		}
		if (StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("password");
		}

		// Authenticator = new HttpBasicAuthenticator(userName, password);
		// ClearHandlers();
		// AddHandler("text/xml", new AssetDeserializer());
	}

	public AssetClient(String baseUrl, String accessToken) {
		this._baseUrl = baseUrl;
		this._accessToken = accessToken;

		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken");
		}

		// AccessTokenAuthenticator authenticator = new
		// AccessTokenAuthenticator(accessToken);
		// ClearHandlers();
		// AddHandler("text/xml", new AssetDeserializer());

	}

	public IFluentQueryBuilder query(String assetSource) {
		@SuppressWarnings("unchecked")
		Function<String, List<IAssetBase>> execute = (String query) -> {
			Reader dataReader = null;
			CloseableHttpClient httpclient = HttpClients.createDefault();

			ArrayList<String> results = null;
			
			HttpGet request = new HttpGet(this._baseUrl + query);

			request.addHeader("Authorization", "Bearer " + _accessToken);

			List<IAssetBase> assets = new ArrayList<IAssetBase>();
			try {
				response = httpclient.execute(request);

				dataReader = new InputStreamReader(response.getEntity()
						.getContent());

				// results = (ArrayList<String>)
				// IOUtils.readLines(response.getEntity().getContent(),
				// "UTF-8");

				// var results = response.Data as IList<dynamic>;
				Document doc = XMLHandler.buildDocument(dataReader, "");

				XPath xpath = XPathFactory.newInstance().newXPath();
				NodeList assetnodes;

				assetnodes = (NodeList) xpath.evaluate("//AssetType",
						doc.getDocumentElement(), XPathConstants.NODESET);
				for (int assetIndex = 0; assetIndex < assetnodes.getLength(); ++assetIndex) {
					Element element = (Element) assetnodes.item(assetIndex);

					assets.add(new AssetBase(element));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// List<IAssetBase> assets = new
			// ArrayList<IAssetBase>(results.size());

			// (results).forEach(item->assets.add(new AssetBase(dataReader)));

			// (results).forEach(item->assets.add(new AssetBase(item)));

			return assets;
		};
		return new FluentQueryBuilder(assetSource, execute);
	}
}
