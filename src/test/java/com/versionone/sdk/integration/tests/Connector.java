package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IServices;


public class Connector {

	private static V1Connector _connector;
	private static String _instanceUrl;
	private static String _username;
	private static String _password;
	private static String _accessToken;
	private static IServices _services; 
	private static Oid _projectId;

	@BeforeClass
	public static void beforeRun() throws Exception {
		System.out.println("Running connector integration tests...");
		_connector = APIClientIntegrationTestSuiteIT.get_connector();
		_instanceUrl = APIClientIntegrationTestSuiteIT.get_instanceUrl();
		_username = APIClientIntegrationTestSuiteIT.get_username();
		_password = APIClientIntegrationTestSuiteIT.get_password();
		_accessToken = APIClientIntegrationTestSuiteIT.get_accessToken();
		_services = APIClientIntegrationTestSuiteIT.get_services();
		_projectId = APIClientIntegrationTestSuiteIT.get_projectId();
	}
	
	@Test()
	public void ConnectorWithUsernameAndPassword() throws MalformedURLException, V1Exception {

		V1Connector connector = V1Connector.withInstanceUrl(_instanceUrl)
				.withUserAgentHeader("TestApp", "1.0")
				.withUsernameAndPassword(_username, _password)
				.build();
		
		IServices services = new Services(connector);
		Oid oid = services.getLoggedIn();
		assertNotNull(oid);
	}

	@Test
	public void ConnectorWithAccessToken() throws Exception {
		
		V1Connector connector = V1Connector.withInstanceUrl(_instanceUrl)
				.withUserAgentHeader("TestApp", "1.0")
				.withAccessToken(_accessToken)
				.build();
		
		IServices services = new Services(connector);
		Oid oid = services.getLoggedIn();
		assertNotNull(oid);
	}

//	@Test()
//	public void saveAndUpdateTest() throws V1Exception, MalformedURLException {
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.build();
//
//		Services services = new Services(connector);
//
//		Oid projectId = Oid.fromToken("Scope:0", services.getMeta());
//		IAssetType storyType = services.getMeta().getAssetType("Story");
//		Asset newStory = services.createNew(storyType, projectId);
//		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
//		newStory.setAttributeValue(nameAttribute, "My New Story");
//		services.save(newStory);
//
//		assertNotNull("Token: " + newStory.getOid().getToken());
//		assertEquals("Scope:0", newStory.getAttribute(storyType.getAttributeDefinition("Scope")).getValue().toString());
//		assertEquals("My New Story", newStory.getAttribute(nameAttribute).getValue().toString());
//
//		Oid storyId = newStory.getOid();
//		Query query = new Query(storyId);
//		nameAttribute = services.getMeta().getAssetType("Story").getAttributeDefinition("Name");
//		query.getSelection().add(nameAttribute);
//		QueryResult result = services.retrieve(query);
//		Asset story = result.getAssets()[0];
//		String newName = "This is my New Name";
//		story.setAttributeValue(nameAttribute, newName);
//		services.save(story);
//
//		assertEquals("This is my New Name", story.getAttribute(nameAttribute).getValue().toString());
//
//	}

//	@Test()
//	public void queryTest() throws V1Exception, MalformedURLException {
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.build();
//
//		Services services = new Services(connector);
//
//		IAssetType assetType = services.getAssetType("Member");
//		Query query = new Query(assetType);
//		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
//		query.getSelection().add(nameAttribute);
//		IAttributeDefinition isSelf = assetType.getAttributeDefinition("IsSelf");
//		FilterTerm filter = new FilterTerm(isSelf);
//		filter.equal("true");
//
//		QueryResult result = services.retrieve(query);
//		assertNotNull(result);
//		assertTrue(result.getAssets().length > 0);
//	}

//	@Test(expected = MalformedURLException.class)
//	public void validatePathTest() throws V1Exception, MalformedURLException {
//
//		url = "http//localhost/versionone/";
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.build();
//	}

//	@Test()
//	@Ignore
//	public void withAccessTokenThruAProxyTest() throws V1Exception, MalformedURLException {
//
//		String accessToken = "access_token";
//		URI address = null;
//		try {
//			address = new URI("http://localhost:808");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withAccessToken(accessToken).withProxy(proxy)
//				.build();
//
//		Services services = new Services(connector);
//		Oid oid = services.getLoggedIn();
//		assertNotNull(oid);
//	}
//
//	@Test()
//	@Ignore
//	public void connetionWithProxyUsingUsernameAndPasswordTest() throws V1Exception, MalformedURLException {
//
//		URI address = null;
//		try {
//			address = new URI("http://localhost:808");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withUsernameAndPassword(username, password)
//				.withProxy(proxy).build();
//
//		Services services = new Services(connector);
//
//		IAssetType assetType = services.getAssetType("Member");
//		Query query = new Query(assetType);
//		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
//		query.getSelection().add(nameAttribute);
//		IAttributeDefinition isSelf = assetType.getAttributeDefinition("IsSelf");
//		FilterTerm filter = new FilterTerm(isSelf);
//		filter.equal("true");
//
//		assertNotNull(assetType);
//		QueryResult result = services.retrieve(query);
//		assertNotNull(result);
//		assertTrue(result.getAssets().length > 0);
//	}
//
//	@Test()
//	@Ignore
//	public void testConnectionNtlm() throws Exception {
//
//		url = "http://localhost/VersionOneNtlm/";
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated().build();
//
//		Services services = new Services(connector);
//		Oid oid = services.getLoggedIn();
//		System.out.println(oid.getAssetType().getDisplayName());
//		assertNotNull(oid);
//	}
//
//	@Test()
//	@Ignore
//	public void testConnectionNtlmWithUsernamePass() throws Exception {
//
//		url = "http://localhost/VersionOneNtlm/";
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated("vplechuc", "moifaku72")
//				.build();
//
//		Services services = new Services(connector);
//		Oid oid = services.getLoggedIn();
//		assertNotNull(oid);
//	}
//
//	@Test()
//	@Ignore
//	public void testConnectionNtlmWithProxy() throws Exception {
//
//		String url = "http://localhost/VersionOne/";
//		URI address = null;
//		try {
//			address = new URI("http://localhost:808");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		ProxyProvider proxy = new ProxyProvider(address, "user1", "user1");
//
//		V1Connector connector = V1Connector.withInstanceUrl(url).withUserAgentHeader("name", "1.0").withWindowsIntegrated().withProxy(proxy).build();
//
//		Services services = new Services(connector);
//
//		IAssetType assetType = services.getAssetType("Member");
//
//		assertNotNull(assetType);
//	}
//
//	/**
//	 * Launch the test.
//	 *
//	 * @param args
//	 *            the command line arguments
//	 */
//	public static void main(String[] args) {
//		new org.junit.runner.JUnitCore().run(Connector.class);
//	}

}