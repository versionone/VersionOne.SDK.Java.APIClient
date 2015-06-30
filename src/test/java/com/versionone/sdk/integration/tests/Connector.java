package com.versionone.sdk.integration.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.exceptions.V1Exception;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IServices;


public class Connector {

	private static String _instanceUrl;
	private static String _username;
	private static String _password;
	private static String _accessToken;
	private static String _instanceUrlNTLM;
	private static Oid _projectId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Running connector integration tests...");
		_instanceUrl = APIClientIntegrationTestSuiteIT.get_instanceUrl();
		_username = APIClientIntegrationTestSuiteIT.get_username();
		_password = APIClientIntegrationTestSuiteIT.get_password();
		_accessToken = APIClientIntegrationTestSuiteIT.get_accessToken();
		_projectId = APIClientIntegrationTestSuiteIT.get_projectId();
	}
	
	@Test()
	public void ConnectorWithUsernameAndPassword() throws MalformedURLException, V1Exception {

		V1Connector connector = V1Connector.withInstanceUrl(_instanceUrl)
				.withUserAgentHeader("JavaSDKIntegrationTests", "1.0")
				.withUsernameAndPassword(_username, _password)
				.build();
		
		IServices services = new Services(connector);
		Oid oid = services.getLoggedIn();
		assertNotNull(oid);
	}

	@Test
	public void ConnectorWithAccessToken() throws Exception {
		
		V1Connector connector = V1Connector.withInstanceUrl(_instanceUrl)
				.withUserAgentHeader("JavaSDKIntegrationTests", "1.0")
				.withAccessToken(_accessToken)
				.build();
		
		IServices services = new Services(connector);
		Oid oid = services.getLoggedIn();
		assertNotNull(oid);
	}
	
	@Test
	public void ConnectorWithOauthEndpoints() throws Exception {
		
//		V1Connector connector = V1Connector.withInstanceUrl(_instanceUrlNTLM)
//				.withUserAgentHeader("JavaSDKIntegrationTests", "1.0")
//				.withAccessToken(_accessToken)
//				.useOAuthEndpoints()
//				.build();
//		
//		IServices _services = new Services(connector);
//		IAssetType storyType = _services.getMeta().getAssetType("Story");
//		Asset newStory = _services.createNew(storyType, _projectId);
//		IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
//		String name = "Test Story " + _projectId + " Query single asset";
//		newStory.setAttributeValue(nameAttribute, name);
//		_services.save(newStory);
//
//		Query query = new Query(newStory.getOid());
//		query.getSelection().add(nameAttribute);
//		Asset story = _services.retrieve(query).getAssets()[0];
//
//		assertNotNull(story);
//		assertTrue(story.getAttribute(nameAttribute).getValue().toString().equals(name));

	}

}