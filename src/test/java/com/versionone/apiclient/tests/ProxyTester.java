package com.versionone.apiclient.tests;

import java.net.URI;
import java.net.URISyntaxException;

import com.versionone.apiclient.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("This test required proxy server.")
public class ProxyTester {

	private final static String proxyAddress = "http://proxy:3128";
	private final static String proxyUserName = "user";
	private final static String proxyPassword = "password";

	private final static String v1Path = "http://localhost/VersionOneTest/";
	private final static String v1UserName = "admin";
	private final static String v1Password = "admin";


	@Test
	public void getProjectListTest() throws URISyntaxException, ConnectionException, APIException, OidException {
		URI proxy = new URI(proxyAddress);
		ProxyProvider proxyProvider = new ProxyProvider(proxy, proxyUserName, proxyPassword);

		//Connection with proxy
		IAPIConnector metaConnectorWithProxy = new V1APIConnector(v1Path + "meta.v1/", proxyProvider);
        IMetaModel metaModelWithProxy = new MetaModel(metaConnectorWithProxy);

        ClientConfiguration config = new ClientConfiguration(v1Path, v1UserName, v1Password);
        IAPIConnector dataConnectorWithProxy = new V1APIConnector(v1Path + "rest-1.v1/", config, proxyProvider);
        IServices servicesWithProxy = new Services(metaModelWithProxy, dataConnectorWithProxy);

        //Connection without proxy
		IAPIConnector metaConnector = new V1APIConnector(v1Path + "meta.v1/");
        IMetaModel metaModel = new MetaModel(metaConnector);
        IAPIConnector dataConnector = new V1APIConnector(v1Path + "rest-1.v1/", config);
        IServices services = new Services(metaModel, dataConnector);

        IAssetType projectTypeWithProxy = metaModelWithProxy.getAssetType("Scope");
        Query scopeQueryWithProxy = new Query(projectTypeWithProxy);
        QueryResult resultWithProxy = servicesWithProxy.retrieve(scopeQueryWithProxy);

        IAssetType projectType = metaModel.getAssetType("Scope");
        Query scopeQuery = new Query(projectType);
        QueryResult result = services.retrieve(scopeQuery);

        Assert.assertEquals(result.getAssets().length, resultWithProxy.getAssets().length);

	}
}
