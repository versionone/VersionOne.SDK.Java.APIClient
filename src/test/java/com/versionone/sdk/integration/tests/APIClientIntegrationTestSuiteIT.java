package com.versionone.sdk.integration.tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1Connector;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IServices;

@RunWith(Suite.class)

//NOTE: Add classes here to include in integration test run using Maven "verify" goal.
@Suite.SuiteClasses({ 
	AttachmentsAndImages.class,	
	Configuration.class,
	Connector.class,
	CreateAssets.class,
	Localization.class,
	Operations.class,
	QueryAPI.class,	
	QueryAssets.class,
	UpdateAssets.class
	})

public class APIClientIntegrationTestSuiteIT {

	private static String _instanceUrl;
	private static V1Connector _connector;
	private static String _username;
	private static String _password;
	private static String _accessToken;
	private static IServices _services;
	private static Oid _projectId;
	private static String _use_oauth;
	
	
	@BeforeClass
	public static void beforeRun() throws Exception {
		
		System.out.println("\n*** Beginning Integration Test Run ***\n");
		
		//Read the instance URL from the properties file in current directory.
		Properties properties = null;
		try (FileReader reader = new FileReader("EnvFile.properties")) {
			properties = new Properties();
			properties.load(reader);
			_instanceUrl = properties.getProperty("V1_INSTANCE_URL");
			_username = properties.getProperty("V1_USERNAME");
			_password = properties.getProperty("V1_PASSWORD");
			_accessToken = properties.getProperty("V1_ACCESS_TOKEN");
			_use_oauth = properties.getProperty("USE_OAUTH");
			
			System.out.println("INSTANCE URL: " + _instanceUrl);
			System.out.println("USERNAME: " + _username);
			System.out.println("PASSWORD: " + _password);
			System.out.println("ACCESS TOKEN: " + _accessToken + "\n");			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Check that we have an instance URL.
		if (null == _instanceUrl) {
			String message = "No instance URL found. Ending integration test run.";
			System.out.println(message);
			throw new RuntimeException(message);
		}
		
		//Check that we have an access token.
		if (null == _accessToken) {
			String message = "No access token found. Ending integration test run.";
			System.out.println(message);
			throw new RuntimeException(message);
		}
		
		//Create the V1Connector.
		if (_use_oauth.equals("true")){
					V1Connector conn = new V1Connector();
					_connector = conn.withInstanceUrl(_instanceUrl)
							.withUserAgentHeader("JavaSDKIntegrationTests", "1.0")
							.withAccessToken(_accessToken)
							.useOAuthEndpoints()
							.build();
		}else{
			V1Connector conn = new V1Connector();
				_connector = conn
						.withInstanceUrl(_instanceUrl)
						.withUserAgentHeader("JavaSDKIntegrationTests", "1.0")
						.withAccessToken(_accessToken)
						.build();
		}

		//Create a new project for integration test assets.
		_services = new Services(_connector);
		Oid projectId = Oid.fromToken("Scope:0", _services.getMeta());
		IAssetType assetType = _services.getMeta().getAssetType("Scope");
		Asset newAsset = _services.createNew(assetType, projectId);
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
		newAsset.setAttributeValue(nameAttribute, "Java SDK Integration Tests: " + DateTime.now());
		_services.save(newAsset);
		_projectId = newAsset.getOid().getMomentless();
	}
	
	@AfterClass
	public static void afterRun() {
		System.out.println("\n*** Ending Integration Test Run ***\n");
	}

	public static String get_instanceUrl() {
		return _instanceUrl;
	}
	
	public static V1Connector get_connector() {
		return _connector;
	}
	
	public static String get_username() {
		return _username;
	}
	
	public static String get_password() {
		return _password;
	}
	
	public static String get_accessToken() {
		return _accessToken;
	}
	
	public static IServices get_services() {
		return _services;
	}

	public static Oid get_projectId() {
		return _projectId;
	}

	public static String get_oauth() {
		return  _use_oauth;
	}
}
