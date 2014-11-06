package com.versionone.apiclient.integration.tests;

import junit.framework.Assert;

import org.junit.Test;

import com.versionone.apiclient.APIException;
import com.versionone.apiclient.ConnectionException;
import com.versionone.apiclient.V1Configuration;

public class ConfigurationTests {

	//add config test
	@Test
	public void testConfig() throws ConnectionException, APIException {
		V1Configuration v1config = APIClientIntegrationTestSuiteIT.get_context().getV1Configuration();
		Assert.assertTrue(v1config.isEffortTracking());
	}
	
	@Test
	public void testGetV1Url() {
		String v1Url = APIClientIntegrationTestSuiteIT.getInstanceUrl().getV1Url();
		Assert.assertTrue(v1Url.length() != 0);
		Assert.assertTrue(v1Url.startsWith("http"));
		Assert.assertTrue(v1Url.endsWith("/"));
	}

	@Test
	public void testGetDataUrl() {
		String dataUrl = APIClientIntegrationTestSuiteIT.getInstanceUrl().getDataUrl();
		Assert.assertTrue(dataUrl.length() != 0);
		Assert.assertTrue(dataUrl.endsWith("rest-1.v1/")); // reasonable assumption...for now
	}

	@Test
	public void testGetMetaUrl() {
		String metaUrl = APIClientIntegrationTestSuiteIT.getInstanceUrl().getMetaUrl();
		Assert.assertTrue(metaUrl.length() != 0);
		Assert.assertTrue(metaUrl.endsWith("meta.v1/")); // reasonable assumption...for now
	}

	@Test
	public void testGetUserName() {
		String userName = APIClientIntegrationTestSuiteIT.getInstanceUrl().getV1UserName();
		Assert.assertTrue(userName.length() != 0);
	}

	@Test
	public void testGetPassword() {
		String password = APIClientIntegrationTestSuiteIT.getInstanceUrl().getV1Password();
		Assert.assertTrue(password.length() != 0);
	}

	@Test
	public void testGetConfigUrl() {
		String configUrl = APIClientIntegrationTestSuiteIT.getInstanceUrl().getConfigUrl();
		Assert.assertTrue(configUrl.length() != 0);
	}

	@Test
	public void testGetProxyUrl() {
		String proxyUrl = APIClientIntegrationTestSuiteIT.getInstanceUrl().getProxyUrl();
		Assert.assertTrue(proxyUrl.length() != 0);
		Assert.assertTrue(proxyUrl.startsWith("http"));
	}

	@Test
	public void testGetProxyUserName() {
		String userName = APIClientIntegrationTestSuiteIT.getInstanceUrl().getProxyUserName();
		Assert.assertTrue(userName.length() != 0);
	}

	@Test
	public void testGetProxyPassword() {
		String password = APIClientIntegrationTestSuiteIT.getInstanceUrl().getProxyPassword();
		Assert.assertTrue(password.length() != 0);
	}
}
