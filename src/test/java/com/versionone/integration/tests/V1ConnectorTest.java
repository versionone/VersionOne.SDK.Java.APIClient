package com.versionone.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.ProxyProvider;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.filters.FilterTerm;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.services.QueryResult;

/**
 * The class <code>V1ConnectorTest</code> contains tests for the class <code>{@link V1Connector}</code>.
 *
 * 
 * @version $Revision: 1.0 $
 */
public class V1ConnectorTest {

	public static String url;
	public static String username;
	public static String password;
	public V1Connector result;

	@Before
	public void setUp() throws Exception {
		// add additional set up code here
		url = "http://localhost//VersionOne/";
		username = "admin";
		password = "1234";
	}

	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

	@Test()
	public void v1ConnectorTest() throws Exception {
		String url = "http://localhost/versionone/";

		V1Connector result = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated(username, password).build();

		assertNotNull(result);
	}

	@Test()
	public void saveAndUpdateTest() throws V1Exception, MalformedURLException {

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
				.build();

		Services services = new Services(connector);

		Oid projectId = Oid.fromToken("Scope:0", services.getMeta());
		IAssetType storyType = services.getMeta().getAssetType("Story");
		Asset newStory = services.createNew(storyType, projectId);
		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
		newStory.setAttributeValue(nameAttribute, "My New Story");
		services.save(newStory);

		assertNotNull("Token: " + newStory.getOid().getToken());
		assertEquals("Scope:0", newStory.getAttribute(storyType.getAttributeDefinition("Scope")).getValue().toString());
		assertEquals("My New Story", newStory.getAttribute(nameAttribute).getValue().toString());

		Oid storyId = newStory.getOid();
		Query query = new Query(storyId);
		nameAttribute = services.getMeta().getAssetType("Story").getAttributeDefinition("Name");
		query.getSelection().add(nameAttribute);
		QueryResult result = services.retrieve(query);
		Asset story = result.getAssets()[0];
		String newName = "This is my New Name";
		story.setAttributeValue(nameAttribute, newName);
		services.save(story);

		assertEquals("This is my New Name", story.getAttribute(nameAttribute).getValue().toString());

	}

	@Test()
	public void queryTest() throws V1Exception, MalformedURLException {

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
				.build();

		Services services = new Services(connector);

		IAssetType assetType = services.getAssetType("Member");
		Query query = new Query(assetType);
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
		query.getSelection().add(nameAttribute);
		IAttributeDefinition isSelf = assetType.getAttributeDefinition("IsSelf");
		FilterTerm filter = new FilterTerm(isSelf);
		filter.equal("true");

		QueryResult result = services.retrieve(query);
		assertNotNull(result);
		assertTrue(result.getAssets().length > 0);
	}

	@Test(expected = MalformedURLException.class)
	public void validatePathTest() throws V1Exception, MalformedURLException {

		url = "http//localhost/versionone/";

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
				.build();
	}

	@Test()
	public void withAccessTokenTest() throws V1Exception, MalformedURLException {

		String accessToken = "access_token";

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withAccessToken(accessToken).build();

		Services services = new Services(connector);
		Oid oid = services.getLoggedIn();
		assertNotNull(oid);
	}

	@Test()
	@Ignore
	public void withAccessTokenThruAProxyTest() throws V1Exception, MalformedURLException {

		String accessToken = "access_token";
		URI address = null;
		try {
			address = new URI("http://localhost:808");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withAccessToken(accessToken).withProxy(proxy)
				.build();

		Services services = new Services(connector);
		Oid oid = services.getLoggedIn();
		assertNotNull(oid);
	}

	@Test()
	@Ignore
	public void connetionWithProxyUsingUsernameAndPasswordTest() throws V1Exception, MalformedURLException {

		URI address = null;
		try {
			address = new URI("http://localhost:808");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
				.withProxy(proxy).build();

		Services services = new Services(connector);

		IAssetType assetType = services.getAssetType("Member");
		Query query = new Query(assetType);
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
		query.getSelection().add(nameAttribute);
		IAttributeDefinition isSelf = assetType.getAttributeDefinition("IsSelf");
		FilterTerm filter = new FilterTerm(isSelf);
		filter.equal("true");

		assertNotNull(assetType);
		QueryResult result = services.retrieve(query);
		assertNotNull(result);
		assertTrue(result.getAssets().length > 0);
	}

	@Test()
	public void testConnectionNtlm() throws Exception {

		url = "http://localhost/VersionOneNtlm/";

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated().build();

		Services services = new Services(connector);
		Oid oid = services.getLoggedIn();
		System.out.println(oid.getAssetType().getDisplayName());
		assertNotNull(oid);
	}

	@Test()
	@Ignore
	public void testConnectionNtlmWithUsernamePass() throws Exception {

		url = "http://localhost/VersionOneNtlm/";

		V1Connector connector = V1Connector.withInstanceUrl(url)
				.withUserAgentHeader("name", "1.0")
				.withWindowsIntegrated("vplechuc", "moifaku72")
				.build();

		Services services = new Services(connector);
		Oid oid = services.getLoggedIn();
		assertNotNull(oid);
	}

	@Test()
	@Ignore
	public void testConnectionNtlmWithProxy() throws Exception {

		String url = "http://localhost/VersionOne/";
		URI address = null;
		try {
			address = new URI("http://localhost:808");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");

		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated().withProxy(proxy).build();

		Services services = new Services(connector);

		IAssetType assetType = services.getAssetType("Member");

		assertNotNull(assetType);
	}


	/**
	 * Launch the test.
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(V1ConnectorTest.class);
	}

}