package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.versionone.apiclient.V1APIConnector;

public class APIClient_Connection {


	private static V1APIConnector dataConnector;


	private final static String V1_USERNAME = "admin";
	private final static String V1_PASSWORD = "admin";

	private String data_url; 


	/**
	 * Test basic connection to instance Connections (basic|windows|oauth): BASIC only for now
	 * 
	 * @throws Exception
	 */
	

	@Test
	public void testVerifyConnectionInstance() throws Exception {

		 data_url =  APIClientSuiteIT.getInstanceUrl() + "/rest-1.v1/";
		try {
			dataConnector = new V1APIConnector(data_url, V1_USERNAME, V1_PASSWORD);

			assertNotNull(dataConnector);


		} catch (Exception ex) {
			throw ex;
		}
	}

	// Connections with/without Proxy: Without only for now

	// Configurations (like tracking levels)

}
