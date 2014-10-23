package com.versionone.apiclient.integration.tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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

	private static String instanceUrl;
	
	@BeforeClass
	public static void beforeRun() throws Exception  {
		
		//Read the instance URL from the properties file in current directory.
		Properties properties;
		try (FileReader reader = new FileReader("EnvFile.properties")) {
			properties = new Properties();
			properties.load(reader);
			instanceUrl = properties.getProperty("V1_TEST_INSTANCE");
			System.out.println("TARGET INSTANCE: " + instanceUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Check that we have an instance URL.
		if (null == instanceUrl) {
			String message = "No instance URL found. Ending integration test run.";
			System.out.println(message);
			throw new Exception(message);
		}
	}
	
	public static String getInstanceUrl() {
		return instanceUrl;
	}

}
