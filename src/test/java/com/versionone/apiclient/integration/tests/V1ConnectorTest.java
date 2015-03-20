package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import jdk.nashorn.internal.ir.annotations.Ignore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;





/**
 * The class <code>V1ConnectorTest</code> contains tests for the class <code>{@link V1Connector}</code>.
 *
 * 
 * @author vplechuc
 * @version $Revision: 1.0 $
 */
public class V1ConnectorTest {
	
	String url;
	private String username;
	private String password;
	private V1Connector result;
	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *             if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 20/03/15 15:50
	 */
	@Before
	public void setUp() throws Exception {
		// add additional set up code here
		url = "http://localhost/versionone";
		username = "admin";
		password = "admin";
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 20/03/15 15:50
	 */
	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}
	
	
	/**
	 * Run the V1Connector(String) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 20/03/15 15:50
	 */
	@Test()
	public void testV1Connector_1() throws Exception {
		String url = "http://localhost/versionone/";

		V1Connector result = V1Connector.withInstanceUrl(url)
					.withUserAgentHeader("name",  "1.0")
					.withWindowsIntegrated(username, password)
					.build();

		// add additional test code here
		assertNotNull(result);
	}



	/**
	 * Run the void useDataAPI() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 20/03/15 15:50
	 */
	@Test
	@Ignore
	public void testUseDataAPI_1() throws Exception {
		V1Connector fixture = V1Connector.withInstanceUrl(url)
				.withUserAgentHeader("", "")
				.withWindowsIntegrated(username, password)
				.build();

		fixture.useDataAPI();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		// com.versionone.apiclient.exceptions.V1Exception: Error processing url
		// at com.versionone.apiclient.V1Connector.<init>(V1Connector.java:107)
	}


	/**
	 * Run the V1Connector.ISetUserAgentMakeRequest withInstanceUrl(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 20/03/15 15:50
	 */
	@Test(expected = com.versionone.apiclient.exceptions.V1Exception.class)
	public void testWithInstanceUrl_1() throws Exception {
		String versionOneInstanceUrl = "";

		V1Connector.ISetUserAgentMakeRequest result = V1Connector.withInstanceUrl(versionOneInstanceUrl);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Launch the test.
	 *
	 * @param args
	 *            the command line arguments
	 *
	 * @generatedBy CodePro at 20/03/15 15:50
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(V1ConnectorTest.class);
	}
}