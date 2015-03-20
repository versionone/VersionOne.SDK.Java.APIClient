package com.versionone.apiclient.integration.tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;

public class V1ConnectorTest {

	
	 String V1_URL;
	 String V1_USERNAME;
	 String V1_PASSWORD;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		V1_URL = "http://localhost/versionone/";
		 V1_USERNAME = "admin" ;
		 V1_PASSWORD = "admin";
	}

	@Test
	public void testWithInstanceUrl() throws Exception {
//		throw new RuntimeException("not yet implemented");
		V1Connector testMe = V1Connector
				.withInstanceUrl(V1_URL)
				.withUserAgentHeader("", "")
				.withWindowsIntegrated(V1_USERNAME, V1_PASSWORD).build();
	 Assert.assertNotNull(testMe);
	}

	@Test
	public void testGetDataString() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testSendData() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testBeginRequest() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testEndRequest() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	
	//testing new V1Connector
	@Test()
	public void testInvalidUrlV1Connector() throws V1Exception {
		V1Connector testMe = V1Connector
				.withInstanceUrl(V1_URL)
				.withUserAgentHeader("", "")
				.withWindowsIntegrated(V1_USERNAME, V1_PASSWORD).build();
	 Assert.assertNotNull(testMe);
	
		}

}
