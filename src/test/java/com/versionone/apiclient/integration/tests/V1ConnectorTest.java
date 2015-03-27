package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.services.QueryResult;

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
		url = "http://localhost/versionone/";
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
	//@Test()
	public void testV1Connector_1() throws Exception {
		String url = "http://localhost/versionone/";

		V1Connector result = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated(username, password).connet();

		assertNotNull(result);
	}

	@Test()
	public void testInvalidUser() throws V1Exception {
		//username = "damin";

		V1Connector connector = V1Connector
				.withInstanceUrl(url)
				.withUserAgentHeader("name", "1.0")
				.withUsernameAndPassword(username, password)
				.connet();

		Services services = new Services(connector);

	    Oid oid = Oid.fromToken("Scope:0", services.get_meta());
        IAssetType storyType = services.get_meta().getAssetType("Story");
        Asset firstStory = services.createNew(storyType, oid);
        IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
        firstStory.setAttributeValue(nameAttribute, "Services Test Story");
        services.save(firstStory);

       assertNotNull(firstStory.getOid());
	}

	// @Test(expected = ConnectionException.class)
	// public void testInvalidUrl() throws ConnectionException {
	// // V1APIConnector testMe = new V1APIConnector(V1_URL,V1_USERNAME, V1_PASSWORD);
	// // testMe.getData("rest-1.v1/bogus");
	// }

	// @Test
	// public void testValidUser() throws ConnectionException {
	// // V1APIConnector testMe = new V1APIConnector(V1_URL, V1_USERNAME, V1_PASSWORD);
	// // Reader results = testMe.getData("/rest-1.v1/Data/Scope/0");
	// // Assert.assertTrue(results != null);
	// }

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