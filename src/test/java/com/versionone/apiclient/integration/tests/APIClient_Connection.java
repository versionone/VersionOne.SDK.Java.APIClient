package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.assertNotNull;

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

public class APIClient_Connection {

	private static IMetaModel metaModel;
	private static IServices dataService;
	private static V1APIConnector dataConnector;
	private static V1APIConnector metaConnector;

	private static String V1_USERNAME = "admin";
	private static String V1_PASSWORD = "admin";

	private static String DATA_URL = APIClientSuiteIT.instanceUrl + "/rest-1.v1/";
	private static String META_URL = APIClientSuiteIT.instanceUrl + "/meta.v1/";

	/**
	 * Test basic connection to instance Connections (basic|windows|oauth): BASIC only for now
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_verifyConnectionInstance() throws Exception {

		try {
			dataConnector = new V1APIConnector(DATA_URL, V1_USERNAME, V1_PASSWORD);
			metaConnector = new V1APIConnector(META_URL);

			metaModel = new MetaModel(metaConnector);
			dataService = new Services(metaModel, dataConnector);

			Oid memberId = Oid.fromToken("Member:20", metaModel);
			Query query = new Query(memberId);
			IAttributeDefinition nameAttribute = metaModel.getAttributeDefinition("Member.Username");
			query.getSelection().add(nameAttribute);
			QueryResult result = dataService.retrieve(query);

			assertNotNull(result.getAssets());

			// if (result.getAssets().length > 0)
			// {
			// System.out.println("Instance verified.");
			// }
			// else
			// {
			// String message = "Instance not found.";
			// System.out.println(message);
			// throw new RuntimeException(message);
			// }

		} catch (Exception ex) {
			throw ex;
		}
	}

	// Connections with/without Proxy: Without only for now

	// Configurations (like tracking levels)

}
