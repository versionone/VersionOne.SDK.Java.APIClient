package com.versionone.apiclient.integration.tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.versionone.apiclient.APIConfiguration;

@RunWith(Suite.class)

//NOTE: Add classes here to include in integration test run using Maven "verify" goal.
@Suite.SuiteClasses({
	AssetTests.class,
	ConfigurationTests.class,
	ConnectorTests.class,
	EnvironmentContextTests.class,
	FindBuilderTests.class,
	ModelsAndServicesTests.class,
	ProxyTests.class,
	QueryTests.class,
	UrlsTests.class
})

public class APIClientSuiteIT {

	private static APIConfiguration _config;
	
	@BeforeClass
	public static void beforeRun() throws Exception  {
		 _config = new APIConfiguration();
	}
	
	public static APIConfiguration getInstanceUrl() {
		return _config;
	}
}
