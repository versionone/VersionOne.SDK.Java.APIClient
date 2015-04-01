package com.versionone.apiclient.integration.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.versionone.Oid;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.MetaModel;
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
 * @author vplechuc
 * @version $Revision: 1.0 $
 */
public class V1ConnectorTest {

	public static String url;
	public static String username;
	public static String password;
	public V1Connector result;

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
	public void saveAndUpdateTest() throws V1Exception {

		V1Connector connector = V1Connector
				.withInstanceUrl(url)
				.withUserAgentHeader("name", "1.0")
				.withUsernameAndPassword(username, password)
				.connet();

		Services services = new Services(connector);

	    Oid projectId = Oid.fromToken("Scope:0", services.get_meta());
	    IAssetType storyType = services.get_meta().getAssetType("Story");
	    Asset newStory = services.createNew(storyType, projectId);
	    IAttributeDefinition nameAttribute = storyType.getAttributeDefinition("Name");
	    newStory.setAttributeValue(nameAttribute, "My New Story");
	    services.save(newStory);

//	    System.out.println(newStory.getOid().getToken());
//	    System.out.println(newStory.getAttribute(storyType.getAttributeDefinition("Scope")).getValue());
//	    System.out.println(newStory.getAttribute(nameAttribute).getValue());

	    assertNotNull("Token: " + newStory.getOid().getToken());
	    assertEquals("Scope:0", newStory.getAttribute(storyType.getAttributeDefinition("Scope")).getValue().toString());
	    assertEquals("My New Story", newStory.getAttribute(nameAttribute).getValue().toString());
	    
	
	    Oid storyId = newStory.getOid();
	    Query query = new Query(storyId);
	    nameAttribute = services.get_meta().getAssetType("Story").getAttributeDefinition("Name");
	    query.getSelection().add(nameAttribute);
	    QueryResult result = services.retrieve(query);
	    Asset story = result.getAssets()[0];
	    String oldName = story.getAttribute(nameAttribute).getValue().toString();
	    String newName = "This is my New Name";
	    story.setAttributeValue(nameAttribute, newName);
	    services.save(story);

//	    System.out.println(story.getOid().getToken());
//	    System.out.println("The OLD Name: " + oldName);
//	    System.out.println("The NEW Name: " + story.getAttribute(nameAttribute).getValue());

	    assertEquals("This is my New Name", story.getAttribute(nameAttribute).getValue().toString());
		
	}
	
	//@Test()
	public void pingQuery() throws V1Exception {
	
		V1Connector connector = V1Connector
				.withInstanceUrl(url)
				.withUserAgentHeader("name", "1.0")
				.withUsernameAndPassword(username, password)
				.connet();

		Services services = new Services(connector);
        try 
        {
          IAssetType assetType = services.getAssetType("Member");
          Query query = new Query(assetType);
          IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
          query.getSelection().add(nameAttribute);
          IAttributeDefinition isSelf = assetType.getAttributeDefinition("IsSelf");
          FilterTerm filter = new FilterTerm(isSelf);
          filter.equal("true");
          
          QueryResult result = services.retrieve(query);
          
          if (result.getAssets().length > 0)
          {
                Asset member = result.getAssets()[0];
                System.out.println(member.getOid().getToken());
                System.out.println(member.getAttribute(nameAttribute).getValue());
          }
        } 
        catch (Exception ex)
        {
              System.out.println(ex.getMessage());
        }
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