package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.apiclient.V1Configuration;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.APIException;
import com.versionone.apiclient.exceptions.ConnectionException;

public class Configuration {

	private static V1Connector _connector;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running configuration integration tests...");
		_connector = APIClientIntegrationTestSuiteIT.get_connector();
	}

	@Test
	public void getConfigTest() throws ConnectionException, APIException {

		V1Configuration configuration = new V1Configuration(_connector);

		assertNotNull(configuration.isEffortTracking());
		assertNotNull(configuration.getStoryTrackingLevel());
		assertNotNull(configuration.getDefectTrackingLevel());
		assertNotNull(configuration.getMaxAttachmentSize());
		assertNotNull(configuration.getStoryTrackingLevel());
	}

}
