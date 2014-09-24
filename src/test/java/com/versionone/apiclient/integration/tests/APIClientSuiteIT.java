package com.versionone.apiclient.integration.tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.versionone.Oid;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.IServices;
import com.versionone.apiclient.MetaModel;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;
import com.versionone.apiclient.Services;
import com.versionone.apiclient.V1APIConnector;

@RunWith(Suite.class)

//NOTE: Add classes here to include in integration test run using Maven "verify" goal.
@Suite.SuiteClasses({
//	ProjectSync.class, 
//	WBSSync.class, 
//	TimesheetSync.class
})

public class APIClientSuiteIT {

	private static String instanceUrl;
	
	@BeforeClass
	public static void beforeRun() throws Exception  {
		
		System.out.println("\n*** Beginning Integration Test Run ***\n");
		
		//Read the instance URL from the properties file in current directory.
		Properties properties;
		try (FileReader reader = new FileReader("EnvFile.properties")) {
			properties = new Properties();
			properties.load(reader);
			instanceUrl = properties.getProperty("V1_TEST_INSTANCE");
			System.out.println("TARGET INSTANCE: " + instanceUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Check that we have an instance URL.
		if (null == instanceUrl) {
			String message = "No instance URL found. Ending integration test run.";
			System.out.println(message);
			throw new RuntimeException(message);
		}
		
		//Verify that the instance is available.
		verifyInstance();
		
	}
	
	private static void verifyInstance() throws Exception {
		
		IMetaModel metaModel;
		IServices dataService;
	    V1APIConnector dataConnector;
	    V1APIConnector metaConnector;
		
		String V1_USERNAME = "admin";
		String V1_PASSWORD = "admin";
		
	    String DATA_URL = instanceUrl + "/rest-1.v1/";
	    String META_URL = instanceUrl + "/meta.v1/";
	    
		try
		{
            dataConnector = new V1APIConnector(DATA_URL, V1_USERNAME, V1_PASSWORD);
            metaConnector = new V1APIConnector(META_URL);
            
            metaModel = new MetaModel(metaConnector);
            dataService = new Services(metaModel, dataConnector);
            
            Oid memberId = Oid.fromToken("Member:20", metaModel);
            Query query = new Query(memberId);
            IAttributeDefinition nameAttribute = metaModel.getAttributeDefinition("Member.Username");
            query.getSelection().add(nameAttribute);
            QueryResult result = dataService.retrieve(query);
            
            if (result.getAssets().length > 0)
            {
            	System.out.println("Instance verified.");
            }
            else
            {
    			String message = "Instance not found.";
    			System.out.println(message);
    			throw new RuntimeException(message);
            }
            
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	@AfterClass
	public static void afterRun() {
		System.out.println("\n*** Ending Integration Test Run ***\n");
	}
	
}
