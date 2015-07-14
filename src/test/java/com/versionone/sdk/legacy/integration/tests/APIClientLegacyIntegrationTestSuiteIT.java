package com.versionone.sdk.legacy.integration.tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.versionone.DB.DateTime;
import com.versionone.Oid;
import com.versionone.apiclient.APIConfiguration;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.interfaces.IAssetType;
import com.versionone.apiclient.interfaces.IAttributeDefinition;
import com.versionone.apiclient.interfaces.IMetaModel;
import com.versionone.apiclient.interfaces.IServices;

//NOTE: Add classes here to include in integration test run using Maven "verify" goal.
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	ConnectorsTests.class,
	ConnectorTests.class, 
	AssetTests.class, 
	ConfigurationTests.class, 
	EnvironmentContextTests.class, 
	FindBuilderTests.class,
	ModelsAndServicesTests.class, 
	ProxyTests.class, 
	FindAndQueryTests.class, 
	UrlsTests.class
	})

public class APIClientLegacyIntegrationTestSuiteIT {

	private static APIConfiguration _config;
	private static Oid _projectId; 
	private static IMetaModel _metaModel;
	private static IServices _services;
	private static EnvironmentContext _context;

	
	@BeforeClass
	public static void beforeRun() throws Exception {
		_config = new APIConfiguration();
		createTestSuite();
	}

	public static void createTestSuite() throws Exception {

		 _context = new EnvironmentContext();

		_metaModel = _context.getMetaModel();
		_services = _context.getServices();

		//Create a new project for integration test assets.
		Oid projectId = Oid.fromToken("Scope:0", _metaModel);
		IAssetType assetType = _metaModel.getAssetType("Scope");
		Asset newAsset = _services.createNew(assetType, projectId);
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
		newAsset.setAttributeValue(nameAttribute, "Java SDK Legacy Integration Tests: " + DateTime.now());
		_services.save(newAsset);
		_projectId = newAsset.getOid().getMomentless();
		
	}

	public static APIConfiguration getInstanceUrl() {
		return _config;
	}

	public static Oid get_projectId() {
		return _projectId;
	}

	public static APIConfiguration get_config() {
		return _config;
	}

	public static IMetaModel get_metaModel() {
		return _metaModel;
	}

	public static IServices get_services() {
		return _services;
	}

	public static EnvironmentContext get_context() {
		return _context;
	}
	
}
