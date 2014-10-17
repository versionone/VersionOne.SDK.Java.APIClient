package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.versionone.apiclient.V1APIConnector;

public class APIClient_Connection {


	private static V1APIConnector dataConnector;


	private static String V1_USERNAME = "admin";
	private static String V1_PASSWORD = "admin";

	private static String DATA_URL; //=  APIClientSuiteIT.getInstanceUrl() + "/rest-1.v1/";


	/**
	 * Test basic connection to instance Connections (basic|windows|oauth): BASIC only for now
	 * 
	 * @throws Exception
	 */
	

	@Test
	public void test_verifyConnectionInstance() throws Exception {

		 DATA_URL =  APIClientSuiteIT.getInstanceUrl() + "/rest-1.v1/";
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
