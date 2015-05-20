package com.versionone.sdk.legacy.integration.tests;

import junit.framework.Assert;

import org.junit.Test;

import com.versionone.apiclient.V1Configuration;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;

public class ConfigurationTests {

	//add config test
	@Test
	public void testConfig() throws ConnectionException, APIException {
		V1Configuration v1config = APIClientLegacyIntegrationTestSuiteIT.get_context().getV1Configuration();
		Assert.assertTrue(v1config.isEffortTracking());
	}
	
	@Test
	public void testGetV1Url() {
		String v1Url = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getV1Url();
		Assert.assertTrue(v1Url.length() != 0);
		Assert.assertTrue(v1Url.startsWith("http"));
		Assert.assertTrue(v1Url.endsWith("/"));
	}

	@Test
	public void testGetDataUrl() {
		String dataUrl = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getDataUrl();
		Assert.assertTrue(dataUrl.length() != 0);
		Assert.assertTrue(dataUrl.endsWith("rest-1.v1/")); // reasonable assumption...for now
	}

	@Test
	public void testGetMetaUrl() {
		String metaUrl = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getMetaUrl();
		Assert.assertTrue(metaUrl.length() != 0);
		Assert.assertTrue(metaUrl.endsWith("meta.v1/")); // reasonable assumption...for now
	}

	@Test
	public void testGetUserName() {
		String userName = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getV1UserName();
		Assert.assertTrue(userName.length() != 0);
	}

	@Test
	public void testGetPassword() {
		String password = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getV1Password();
		Assert.assertTrue(password.length() != 0);
	}

	@Test
	public void testGetConfigUrl() {
		String configUrl = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getConfigUrl();
		Assert.assertTrue(configUrl.length() != 0);
	}

	@Test
	public void testGetProxyUrl() {
		String proxyUrl = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getProxyUrl();
		Assert.assertTrue(proxyUrl.length() != 0);
		Assert.assertTrue(proxyUrl.startsWith("http"));
	}

	@Test
	public void testGetProxyUserName() {
		String userName = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getProxyUserName();
		Assert.assertTrue(userName.length() != 0);
	}

	@Test
	public void testGetProxyPassword() {
		String password = APIClientLegacyIntegrationTestSuiteIT.getInstanceUrl().getProxyPassword();
		Assert.assertTrue(password.length() != 0);
	}
}
