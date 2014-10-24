package com.versionone.apiclient.integration.tests;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.apiclient.APIException;
import com.versionone.apiclient.ConnectionException;
import com.versionone.apiclient.IAPIConnector;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.OidException;
import com.versionone.apiclient.ProxyProvider;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1APIConnector;

@Ignore("This test requires a proxy server.")
public class ProxyTests {

	private final static String proxyAddress = "http://proxy:3128";
	private final static String proxyUserName = "user";
	private final static String proxyPassword = "password";

	private final static String V1Url = APIClientSuiteIT.getInstanceUrl().getV1Url();
	private final static String V1UserName = APIClientSuiteIT.getInstanceUrl().getV1UserName();
	private final static String V1Password = APIClientSuiteIT.getInstanceUrl().getV1Password();
	private final static String METAV1 = APIClientSuiteIT.getInstanceUrl().getMetaUrl();
	private final static String RESTV1 = APIClientSuiteIT.getInstanceUrl().getDataUrl();


	@Test
	public void testGetProjectList() throws URISyntaxException, ConnectionException, APIException, OidException {
		URI proxy = new URI(proxyAddress);
		ProxyProvider proxyProvider = new ProxyProvider(proxy, proxyUserName, proxyPassword);

		//Connection with proxy
		IAPIConnector metaConnectorWithProxy = new V1APIConnector(V1Url + METAV1, proxyProvider);
        IMetaModel metaModelWithProxy = new MetaModel(metaConnectorWithProxy);
        IAPIConnector dataConnectorWithProxy = new V1APIConnector(V1Url + RESTV1, V1UserName, V1Password, proxyProvider);
        IServices servicesWithProxy = new Services(metaModelWithProxy, dataConnectorWithProxy);

        //Connection without proxy
		IAPIConnector metaConnector = new V1APIConnector(V1Url + METAV1);
        IMetaModel metaModel = new MetaModel(metaConnector);
        IAPIConnector dataConnector = new V1APIConnector(V1Url + RESTV1, V1UserName, V1Password);
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
