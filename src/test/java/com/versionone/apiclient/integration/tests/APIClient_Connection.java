package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertNotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.versionone.apiclient.V1APIConnector;

public class APIClient_Connection {


	private static V1APIConnector dataConnector;


	private static String V1_USERNAME = "admin";
	private static String V1_PASSWORD = "admin";

	private static String DATA_URL ;


	/**
	 * Test basic connection to instance Connections (basic|windows|oauth): BASIC only for now
	 * 
	 * @throws Exception
	 */
	

	@Test
	public void test_verifyConnectionInstance() throws Exception {
		Properties properties;
		try (FileReader reader = new FileReader("EnvFile.properties")) {
			properties = new Properties();
			properties.load(reader);
			APIClientSuiteIT.instanceUrl = properties.getProperty("V1_TEST_INSTANCE");
			System.out.println("TARGET INSTANCE: " + APIClientSuiteIT.instanceUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DATA_URL =  APIClientSuiteIT.instanceUrl + "/rest-1.v1/";
		try {
			dataConnector = new V1APIConnector(DATA_URL, V1_USERNAME, V1_PASSWORD);

			assertNotNull(dataConnector);


		} catch (Exception ex) {
			throw ex;
		}
	}

	// Connections with/without Proxy: Without only for now

	// Configurations (like tracking levels)

}
