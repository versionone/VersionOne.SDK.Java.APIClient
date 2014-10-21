package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1APIConnector;

/**
 * Test querying a single asset
 * 
 * @throws Exception
 */

public class APIClient_Query {

	private static IMetaModel metaModel;
	private static IServices dataService;
	private static V1APIConnector dataConnector;
	private static V1APIConnector metaConnector;

	private final static String V1_USERNAME = "admin";
	private final static String V1_PASSWORD = "admin";

	private static String dataUrl;
	private static String metaUrl;

	
	private void initialize() {
		dataUrl = APIClientSuiteIT.getInstanceUrl() + "/rest-1.v1/";
		metaUrl = APIClientSuiteIT.getInstanceUrl() + "/meta.v1/";
		
	}
	
	
	@Test
	public void testQuerySingleAsset() throws Exception {
		
		initialize();

		try {
			dataConnector = new V1APIConnector(dataUrl, V1_USERNAME, V1_PASSWORD);
			metaConnector = new V1APIConnector(metaUrl);

			metaModel = new MetaModel(metaConnector);
			dataService = new Services(metaModel, dataConnector);

			Oid memberId = Oid.fromToken("Member:20", metaModel);
			Query query = new Query(memberId);
			IAttributeDefinition nameAttribute = metaModel.getAttributeDefinition("Member.Username");
			query.getSelection().add(nameAttribute);
			QueryResult result = dataService.retrieve(query);

			assertNotNull(result.getAssets());

			Assert.assertEquals("1 asset", 1, result.getAssets().length);

		} catch (Exception ex) {
			throw ex;
		}
	}

	// Query for multiple assets
	//same as single assets

	// Query for attributes (select)
	@Test
	public void testQueryForAtributtes() throws Exception {
		
		initialize();

		try {
			dataConnector = new V1APIConnector(dataUrl, V1_USERNAME, V1_PASSWORD);
			metaConnector = new V1APIConnector(metaUrl);

			metaModel = new MetaModel(metaConnector);
			dataService = new Services(metaModel, dataConnector);
			//not implemented
		
		} catch (Exception ex) {
			throw ex;
		}
	}
	// Query for relations (select)
	// Filter query
	// Find query
	// Sort query
	// Paging query
	// Query history
	// Query "asof"

}
